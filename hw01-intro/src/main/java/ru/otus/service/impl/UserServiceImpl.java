package ru.otus.service.impl;

import ru.otus.service.OutputService;
import ru.otus.service.UserService;

public class UserServiceImpl implements UserService {

    private static final String USERNAME_QUESTION = "What is your name?";

    private final OutputService outputService;

    public UserServiceImpl(OutputService outputService){
        this.outputService = outputService;
    }
    
    @Override
    public void getUser() {
        outputService.output(USERNAME_QUESTION);
    }
}
