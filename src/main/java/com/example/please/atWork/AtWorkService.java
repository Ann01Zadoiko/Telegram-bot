package com.example.please.atWork;

import com.example.please.user.User;
import com.example.please.user.UserRepository;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtWorkService {

    private final AtWorkRepository repository;

    private final UserRepository userRepository;

    private final UserService userService;


    public void atWorkClick(Long chatId){
        User user = userRepository.findByChatId(chatId);

        try {
            if (userService.checkUser(LocalDate.now(), user)){
                AtWork atWork = repository.findAtWorkByUserAndDate(chatId, LocalDate.now());
                atWork.setUser(user);
                repository.save(atWork);

                log.info("true");
                log.info("User wanna be in the list AGAIN!");
            } else {
                AtWork atWork = new AtWork();
                atWork.setDate(LocalDate.now());
                atWork.setUser(user);
                repository.save(atWork);

                log.info("false");
                log.info("User was added to list of employee at work\n" + atWork);
            }
        } catch (HibernateException e){
            log.info("aaaaa");
        }

    }

    public void save(AtWork atWork){
        repository.save(atWork);
    }

    public  List<AtWork> listOfEmployeeOfTheDay(LocalDate date){
        return repository.findAllUsersByDate(date);
    }

    public  String printList(LocalDate date){
        List<AtWork> list = listOfEmployeeOfTheDay(date);
        int number = 1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String s = formatter.format(LocalDate.now());

        for (AtWork atWork: list){
            s += "\n" + number + ". " +
                    atWork.getUser().getFullName() + " ( " +
                    atWork.getUser().getDeparture() + " )";

            ++number;
        }

        return s;
    }

}
