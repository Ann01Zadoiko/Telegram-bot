package com.example.please.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.chatId=:chatId")
    User findByChatId(@Param("chatId") Long chatId);
    boolean existsByChatId(Long chatId);

    @Query("SELECT u FROM User u JOIN FETCH u.atWork aw WHERE aw.date =:date")
    Set<User> findUsersByDate(@Param("date") LocalDate date);

//    @Query(nativeQuery = true, value = "select * from users u join list_of_employees l " +
//            "on u.id=l.id_user where l.date=:date")
//    List<User> findUsersByDate(@Param("date") LocalDate date);
}
