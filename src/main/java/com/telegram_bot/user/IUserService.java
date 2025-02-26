package com.telegram_bot.user;

import java.util.List;

public interface IUserService {

    List<User> listAll();

    void save(User user);

    User getByChatId(Long id);

    boolean existsByChatId(Long id);

    void setNewDay();
}
