package com.example.please.status;

import com.example.please.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatusService {

    private final StatusRepository repository;

    public void save(Status status){
        repository.save(status);

        log.info("\nNew status is " + status.getStatusEnum());
    }

    public Status getByUser (User user){
        return repository.findByUser(user);
    }

    public Status getByStatusEnum(StatusEnum statusEnum){
        return repository.findByStatusEnum(statusEnum);
    }
}
