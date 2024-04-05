package com.example.please.status;

import com.example.please.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

//    Status findByUser(User user);
//
//    Status findByStatusEnum(StatusEnum statusEnum);
}
