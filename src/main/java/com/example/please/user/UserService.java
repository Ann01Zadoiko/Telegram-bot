package com.example.please.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> listAll() {
        return (List<User>) repository.findAll();
    }

    public void save(User user){

        repository.save(user);

        log.info("New user was saved: " + user);
    }


    public Optional<User> getById(Long id){
        return repository.findById(id);
    }

    public boolean existsById(Long id){
        return repository.existsById(id);
    }



}

