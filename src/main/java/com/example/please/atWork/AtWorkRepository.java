package com.example.please.atWork;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AtWorkRepository extends CrudRepository<AtWork, Long> {

    @Query("SELECT aw FROM AtWork aw WHERE aw.date =:date")
    List<AtWork> findAllUsersByDate(LocalDate date);
}
