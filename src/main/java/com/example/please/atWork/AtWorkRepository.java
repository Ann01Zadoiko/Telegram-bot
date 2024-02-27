package com.example.please.atWork;

import com.example.please.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AtWorkRepository extends CrudRepository<AtWork, Long> {

    @Query("FROM AtWork aw JOIN FETCH aw.user WHERE aw.date =:date")
    List<AtWork> findAllUsersByDate(@Param("date") LocalDate date);

    // select * from list_of_employees l join users u on l.id_user=u.id where l.date='2024-02-27'

 //  @Query("FROM AtWork aw JOIN FETCH aw.user WHERE aw.user =:user AND aw.date =:date")
  // AtWork findAtWorkByUserAndDate(@Param("user") User user, @Param("date") LocalDate date);



    //select * from list_of_employees l join users u on l.id_user=u.id where u.id=1 and l.date=    '2024-02-27'
}

