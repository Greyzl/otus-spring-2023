package ru.otus.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.AppProps;
import ru.otus.entity.User;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;
import ru.otus.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final OutputService outputService;

    private final InputService inputService;

    private final MessageSource messageSource;

    private final AppProps appProps;

    public UserServiceImpl(OutputService outputService,
                           InputService inputService,
                           MessageSource messageSource,
                           AppProps appProps){
        this.outputService = outputService;
        this.inputService = inputService;
        this.messageSource = messageSource;
        this.appProps = appProps;
    }
    
    @Override
    public User getUser() {
        String nameQuestionText = messageSource.getMessage(
                "user.messages.name-question", null, appProps.getLocale());
        outputService.output(nameQuestionText);
        String userName = inputService.read();
        return new User(userName);
    }
}
