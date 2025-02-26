package com.telegram.user;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;



@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository repository;

    //got list of users
    public List<User> listAll() {
        List<User> users = repository.findAll();
        log.info("List all: {}", users);
        return users;
    }

    //save a new user
    @Transactional
    public void save(User user){
        repository.save(user);
        repository.flush(); // Принудительно записываем в БД
        log.info("New user was saved: {}", user);
    }

    //get user by chat id
    public User getByChatId(Long id){
        return repository.findByChatId(id).orElse(null);
    }

    //check user (exist)
    public boolean existsByChatId(Long id){
        return repository.existsByChatId(id);
    }

    @Override
    public void setNewDay() {
        List<User> users = listAll();

        for (User user : users) {
            user.setAtWork((byte) 0);
            save(user);
        }
    }

}

