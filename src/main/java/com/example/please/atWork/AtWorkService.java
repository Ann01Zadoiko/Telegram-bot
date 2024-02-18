package com.example.please.atWork;

import com.example.please.user.User;
import com.example.please.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtWorkService {

    private final AtWorkRepository repository;

    private final UserRepository userRepository;

    public void atWorkClick(Long chatId){
        User user = userRepository.findByChatId(chatId);

        AtWork atWork = new AtWork();
        int number =  1;

     //   atWork.setAtWork(true);
        atWork.setDate(LocalDate.now());

        atWork.setUser(user);

        repository.save(atWork);

        log.info("User was added to list of employee at work\n" + atWork);
    }

    public  List<AtWork> listOfEmployeeOfTheDay(LocalDate date){
        return repository.findAllUsersByDate(date);
    }

    public  String printList(LocalDate date){
        List<AtWork> list = listOfEmployeeOfTheDay(date);
        int number = 1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String s = formatter.format(LocalDate.now());
        //String s = String.valueOf(date);

        for (AtWork atWork: list){
            s += "\n" + number + ". " +
                    atWork.getUser().getFullName() + " ( " +
                    atWork.getUser().getDeparture() + " )";

            ++number;
        }

        return s;
    }

}
