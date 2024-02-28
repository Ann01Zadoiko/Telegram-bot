package com.example.please.atWork;


import com.example.please.user.User;
import com.example.please.user.UserRepository;
import com.example.please.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtWorkService {



    private final UserRepository userRepository;

    private final UserService userService;

    private HashMap<LocalDate, List<User>> map = new HashMap<>();
    private List<User> users;


    public HashMap<LocalDate, List<User>> atWorkClick(Long chatId){
        users = userService.listAll();

        Optional<User> user = userRepository.findById(chatId);
        users.add(user.get());
        map.put(LocalDate.now(), users);

        return map;
    }

    private List<User> getListOfUsers(LocalDate date, HashMap<LocalDate, List<User>> users){

        if (users.containsKey(date)) {
            return users.get(date);
        }

        return null;
    }

    public String print(){

        List<User> listOfUsers = getListOfUsers(LocalDate.now(), map);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        int number = 1;
        String s = formatter.format(LocalDate.now());

        for (User user: listOfUsers){
            s += "\n" + number + ". " + user.getFullName();
            ++number;
        }

        return s;
    }



}
