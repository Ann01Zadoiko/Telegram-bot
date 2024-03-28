package com.example.please.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    //got list of users
    public List<User> listAll() {

        List<User> users = new ArrayList<>();
        Iterable<User> iterable = repository.findAll();
        iterable.forEach(users::add);

        log.info("List all: " + users);

        return users;
    }

    //save a new user
    public void save(User user){
        repository.save(user);

        log.info("New user was saved: " + user);
    }

    //get user by chat id
    public User getByChatId(Long id){
        return repository.findByChatId(id);
    }

    //check user (exist)
    public boolean existsByChatId(Long id){
        return repository.existsByChatId(id);
    }

    //update a user
    public void update(User user){
        User user1 = repository.findById(user.getId()).get();
        user1.setFullName(user.getFullName());
        user1.setPassword(user1.getPassword());
        user1.setAtWork(user.getAtWork());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setRoom(user.getRoom());

        log.info(user.getFullName() + " was updated!");

        repository.save(user);
    }

}

