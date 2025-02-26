package com.example.please.user;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> listAll();

    void save(User user);

    User getByChatId(Long id);

    boolean existsByChatId(Long id);

    void setNewDay();
}
