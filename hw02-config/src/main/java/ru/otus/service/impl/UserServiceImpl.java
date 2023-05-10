package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;
import ru.otus.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERNAME_QUESTION = "What is your name?";

    private final OutputService outputService;

    private final InputService inputService;

    public UserServiceImpl(OutputService outputService,
                           InputService inputService){
        this.outputService = outputService;
        this.inputService = inputService;
    }
    
    @Override
    public User getUser() {
        outputService.output(USERNAME_QUESTION);
        String userName = inputService.read();
        return new User(userName);
    }
}
