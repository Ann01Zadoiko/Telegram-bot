package com.example.please.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


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

    public Set<User> listOfUsersByDate(LocalDate date){
        return repository.findUsersByDate(date);
    }

    public boolean checkUser(LocalDate date, Long chatId){
        Set<User> users = listOfUsersByDate(date);

        log.info(users.toString());

        for (User user1: users){
            if (user1.getChatId().equals(chatId)){
                log.info("\nUser again wanna be in the list");
                log.info("chatId");
                log.info("EQUALS");
                log.info(date.toString());
                return true;
            } else {
                log.info("Not\n" + chatId);
                log.info("NOT EQUALS");
                return false;
            }
        }
        return false;
    }
}

