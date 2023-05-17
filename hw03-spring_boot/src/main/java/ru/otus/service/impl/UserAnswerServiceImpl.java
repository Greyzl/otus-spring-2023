package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.formatter.UserAnswerTextFormatter;
import ru.otus.service.InputService;
import ru.otus.service.LocalizedMessageService;
import ru.otus.service.OutputService;
import ru.otus.service.UserAnswerService;
import ru.otus.untils.AnswerOptionUtils;

import java.util.List;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {
    private final OutputService outputService;

    private final InputService inputService;

    private final UserAnswerTextFormatter userAnswerTextFormatter;

    private final LocalizedMessageService localizedMessageService;


    public UserAnswerServiceImpl(OutputService outputService,
                                 InputService inputService,
                                 UserAnswerTextFormatter userAnswerTextFormatter,
                                 LocalizedMessageService localizedMessageService){
        this.outputService = outputService;
        this.inputService = inputService;
        this.userAnswerTextFormatter = userAnswerTextFormatter;
        this.localizedMessageService = localizedMessageService;
    }

    @Override
    public Answer getUserAnswer(Question question) {
        List<Answer> answerOptions = question.getAnswerOptions();
        outputService.output(userAnswerTextFormatter.format(question, answerOptions));
        while (true){
            String chooseOptionText = localizedMessageService.getMessage(
                    "user-answer.messages.choose-option",null);
            outputService.output(chooseOptionText);
            String userAnswerPositionText = inputService.read();
            try {
                int userAnswerPosition = Integer.parseInt(userAnswerPositionText);
                int answerOptionArrayIndex = AnswerOptionUtils.getArrayIndex(userAnswerPosition);
                return answerOptions.get(answerOptionArrayIndex);
            } catch (Exception e){
                String optionNotExistsText = localizedMessageService.getMessage(
                        "user-answer.messages.option-not-exits",null);
                outputService.output(optionNotExistsText);
            }
        }
    }
}
