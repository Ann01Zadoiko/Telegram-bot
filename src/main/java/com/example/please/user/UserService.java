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

    public List<User> listAll() {

        List<User> users = new ArrayList<>();
        Iterable<User> iterable = repository.findAll();
        iterable.forEach(users::add);

        log.info("List all: " + users);

        return users;
    }

    public void save(User user){
        repository.save(user);

        log.info("New user was saved: " + user);
    }


    public User getByChatId(Long id){
        return repository.findByChatId(id);
    }

    public boolean existsByChatId(Long id){
        return repository.existsByChatId(id);
    }

    public void update(User user){

        User user1 = repository.findById(user.getId()).get();
        user1.setFullName(user.getFullName());
        user1.setPassword(user1.getPassword());

        log.info(user.getFullName() + " was updated!");

        repository.save(user);
    }

    public User getById(Long id){
        return repository.findById(id).get();
    }
}

