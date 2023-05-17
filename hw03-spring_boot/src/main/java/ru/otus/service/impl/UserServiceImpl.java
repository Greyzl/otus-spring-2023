package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.service.InputService;
import ru.otus.service.LocalizedMessageService;
import ru.otus.service.OutputService;
import ru.otus.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final OutputService outputService;

    private final InputService inputService;

    private final LocalizedMessageService localizedMessageService;

    public UserServiceImpl(OutputService outputService,
                           InputService inputService,
                           LocalizedMessageService localizedMessageService){
        this.outputService = outputService;
        this.inputService = inputService;
        this.localizedMessageService = localizedMessageService;
    }
    
    @Override
    public User getUser() {
        String nameQuestionText = localizedMessageService.getMessage(
                "user.messages.name-question", null);
        outputService.output(nameQuestionText);
        String userName = inputService.read();
        return new User(userName);
    }
}
