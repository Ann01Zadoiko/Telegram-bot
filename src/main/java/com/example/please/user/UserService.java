package com.example.please.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


//    change to id
//    public void editFullName(Long chatId, String fullName){
//        User user = repository.findByChatId(chatId);
//        user.setFullName(fullName);
//
//        repository.save(user);
//        log.info("/nChange user full name: " + user.getFullName() + " with id: " + user.getId() + " and chat id: "
//         + user.getChatId());
//    }
//
//    public void editDeparture(Long chatId, String departure){
//        User user = repository.findByChatId(chatId);
//        user.setDeparture(departure);
//
//        repository.save(user);
//        log.info("/nChange user departure: " + user.getDeparture() + " with id: " + user.getId() + " and chat id: "
//                + user.getChatId());
//    }

}

