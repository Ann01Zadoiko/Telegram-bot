package com.example.please.status;

import com.example.please.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public void save(Status status){

        statusRepository.save(status);

        log.info("\nStatus was created!");
    }

    public List<Status> listAll(){
        return statusRepository.findAll();
    }

}
