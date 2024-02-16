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

//    public List<User> getAll(){
//        List<User> users = (List<User>) repository.findAll();
//        return repository.findAll();
//    }

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
}
