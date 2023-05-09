package ru.otus.service.impl;

import org.springframework.stereotype.Service;
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
    private static final String ENTER_ANSWER_TEXT = "Please, enter the number of option";

    private static final String OPTION_ANSWER_NOT_EXIST = "Such option doesn't exist, please try again";

    private final OutputService outputService;

    private final InputService inputService;

    private final UserAnswerTextFormatter userAnswerTextFormatter;

    public UserAnswerServiceImpl(OutputService outputService,
                                 InputService inputService,
                                 UserAnswerTextFormatter userAnswerTextFormatter){
        this.outputService = outputService;
        this.inputService = inputService;
        this.userAnswerTextFormatter = userAnswerTextFormatter;
    }

    @Override
    public Answer getUserAnswer(Question question) {
        List<Answer> answerOptions = question.getAnswerOptions();
        outputService.output(userAnswerTextFormatter.format(question, answerOptions));
        while (true){
            outputService.output(ENTER_ANSWER_TEXT);
            String userAnswerPositionText = inputService.read();
            try {
                int userAnswerPosition = Integer.parseInt(userAnswerPositionText);
                int answerOptionArrayIndex = AnswerOptionUtils.getArrayIndex(userAnswerPosition);
                return answerOptions.get(answerOptionArrayIndex);
            } catch (Exception e){
                outputService.output(OPTION_ANSWER_NOT_EXIST);
            }
        }
    }
}
