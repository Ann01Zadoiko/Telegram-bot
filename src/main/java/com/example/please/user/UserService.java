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

    //got list of users
    public List<User> listAll() {

        List<User> users = repository.findAll();

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

}

