package com.example.please.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Iterable<User> listAll() {
        return repository.findAll();
    }

    public void save(User user){

        repository.save(user);

        log.info("New user was saved: " + user);
    }

    public User getByChatId(Long chatId){
        return repository.findByChatId(chatId);
    }

    public boolean existsByChatId(Long chatId){
        return repository.existsByChatId(chatId);
    }

    public List<User> listOfUsersByDate(LocalDate date){
        return repository.findUsersByDate(date);
    }

    public boolean checkUser(LocalDate date, User user){
        List<User> users = listOfUsersByDate(date);

        for (User user1: users){
            if (!user1.equals(user)){
                log.info("\nUser again wanna be in the list");
                log.info(user.toString());
                log.info("EQUALS");
                log.info(date.toString());
                return true;
            } else {
                log.info("Not\n" + user.toString());
                log.info("NOT EQUALS");
                return false;
            }
        }
        return false;
    }
}

