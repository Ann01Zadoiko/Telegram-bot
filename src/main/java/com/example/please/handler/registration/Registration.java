package com.example.please.handler.registration;

import com.example.please.bot.TelegramBot;
import com.example.please.buttons.SettingsButton;
import com.example.please.constant.EnumNotification;
import com.example.please.constant.Steps;
import com.example.please.constant.UserStatus;
import com.example.please.notification.Notification;
import com.example.please.notification.NotificationService;
import com.example.please.user.StatusEnum;
import com.example.please.user.User;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class Registration {

    private final UserStateManager stateManager;
    private final UserService userService;
    private final NotificationService notificationService;

    // --- НАЧАЛО РЕГИСТРАЦИИ ---
    @SneakyThrows
    public void startRegistration(Long chatId, TelegramBot bot) {
        log.info("🚀 Начата регистрация chatId: {}", chatId);
        stateManager.startRegistration(chatId, RegistrationType.START, 1);
        bot.sendMessage(chatId, "📝 " + Steps.START);
    }

    // --- ОБРАБОТКА ЭТАПОВ РЕГИСТРАЦИИ ---
    @SneakyThrows
    public void processRegistrationStep(Long chatId, String messageText, TelegramBot bot) {
        log.info("🔹 processRegistrationStep() вызван для chatId: {}, step: {}", chatId, stateManager.getUserRegistration(chatId).getStep());

        UserRegistration userReg = stateManager.getUserRegistration(chatId);
        User user = userService.getByChatId(chatId);

        switch (userReg.getStep()) {
            case 1:
                log.info("✅ Пользователь ввел имя: {}", messageText);

                if (user == null) {
                    log.info("🔹 Создание нового пользователя...");
                    user = new User();
                    user.setChatId(chatId);
                    user.setAtWork((byte) 0);
                    user.setStatusEnum(StatusEnum.NOTHING);

                    userService.save(user);
                    log.info("✅ Пользователь сохранен в базе: {}", user);

                    Notification notification = new Notification();
                    notification.setTurnOn(true);
                    notification.setTimeOfNotification(EnumNotification.EIGHT_FIFTY_FIVE.getTime());
                    notification.setUser(user);
                    notificationService.save(notification);
                }

                userReg.setFullName(messageText);
                userReg.nextStep();

                log.info("🔄 Обновление регистрации chatId: {}, новый step: {}", chatId, userReg.getStep());
                stateManager.updateUserRegistration(chatId, userReg);

                bot.sendMessage(chatId, "📞 " + Steps.STEP_1);
                log.info("📩 Отправлено сообщение о шаге 2.");
                break;
            case 2:
                if (user == null) {
                    log.error("❌ Ошибка: Пользователь с chatId={} не найден в базе!", chatId);
                    bot.sendMessage(chatId, "⚠ Ошибка: Не удалось найти ваш профиль. Попробуйте начать регистрацию заново.");
                    return;
                }

                log.info("✅ Пользователь ввел номер телефона: {}", messageText);

                user.setPhoneNumber(messageText);
                userService.save(user);

                userReg.setPhoneNumber(messageText);
                userReg.nextStep();
                stateManager.updateUserRegistration(chatId, userReg);

                EnumSet<UserStatus> enumSet = EnumSet.allOf(UserStatus.class);
                enumSet.remove(UserStatus.BACK);
                bot.executeSetting(chatId, new SettingsButton().getButtonsDifferentCount(enumSet), Steps.STEP_2);

                break;
        }
    }
}
