package ru.otus.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.AppProps;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.formatter.UserAnswerTextFormatter;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;
import ru.otus.service.UserAnswerService;
import ru.otus.untils.AnswerOptionUtils;

import java.util.List;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {
    private final OutputService outputService;

    private final InputService inputService;

    private final UserAnswerTextFormatter userAnswerTextFormatter;

    private final MessageSource messageSource;

    private final AppProps appProps;

    public UserAnswerServiceImpl(OutputService outputService,
                                 InputService inputService,
                                 UserAnswerTextFormatter userAnswerTextFormatter,
                                 MessageSource messageSource,
                                 AppProps appProps){
        this.outputService = outputService;
        this.inputService = inputService;
        this.userAnswerTextFormatter = userAnswerTextFormatter;
        this.messageSource = messageSource;
        this.appProps = appProps;
    }

    @Override
    public Answer getUserAnswer(Question question) {
        List<Answer> answerOptions = question.getAnswerOptions();
        outputService.output(userAnswerTextFormatter.format(question, answerOptions));
        while (true){
            String chooseOptionText = messageSource.getMessage(
                    "user-answer.messages.choose-option",null, appProps.getLocale());
            outputService.output(chooseOptionText);
            String userAnswerPositionText = inputService.read();
            try {
                int userAnswerPosition = Integer.parseInt(userAnswerPositionText);
                int answerOptionArrayIndex = AnswerOptionUtils.getArrayIndex(userAnswerPosition);
                return answerOptions.get(answerOptionArrayIndex);
            } catch (Exception e){
                String optionNotExistsText = messageSource.getMessage(
                        "user-answer.messages.option-not-exits",null, appProps.getLocale());
                outputService.output(optionNotExistsText);
            }
        }
    }
}
