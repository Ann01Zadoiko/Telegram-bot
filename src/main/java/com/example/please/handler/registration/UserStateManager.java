package com.example.please.handler.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class UserStateManager {

    // Хранение состояний пользователей
    private final Map<Long, UserRegistration> userRegistrations = new HashMap<>();

    // Запуск регистрации пользователя
    public void startRegistration(Long chatId, RegistrationType type, int step) {
        userRegistrations.put(chatId, new UserRegistration(type, step));
        log.info("new user:{}, {}", chatId, userRegistrations.get(chatId));
    }

    // Проверка, находится ли пользователь в процессе регистрации
    public boolean isUserRegistering(Long chatId) {
        log.info("User is {}", userRegistrations.containsKey(chatId));
        return userRegistrations.containsKey(chatId);
    }

    // Получение данных о регистрации пользователя
    public UserRegistration getUserRegistration(Long chatId) {
        return userRegistrations.get(chatId);
    }

    // Обновление данных о пользователе
    public void updateUserRegistration(Long chatId, UserRegistration registration) {
        userRegistrations.put(chatId, registration);
    }

    // Завершение регистрации (удаление пользователя из списка)
    public void removeUser(Long chatId) {
        userRegistrations.remove(chatId);
    }

    // Определение типа регистрации (музей или жалоба)
    public RegistrationType getUserRegistrationType(Long chatId) {
        UserRegistration reg = userRegistrations.get(chatId);
        if (reg != null) {
            return reg.getType();
        }

        // Проверяем БД только если нет данных в памяти
//        boolean hasComplaint = complaintService.existsByChatId(chatId);
//        boolean hasMuseum = museumService.existsByChatId(chatId);
//
//        if (hasComplaint && !hasMuseum) {
//            return RegistrationType.COMPLAINT;
//        } else if (!hasComplaint && hasMuseum) {
//            return RegistrationType.MUSEUM;
//        } else {
//            return RegistrationType.UNKNOWN;
//        }

        return RegistrationType.START;
    }
}
