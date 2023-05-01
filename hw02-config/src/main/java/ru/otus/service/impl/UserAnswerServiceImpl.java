package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.exception.AnswerNotFoundException;
import ru.otus.formatter.QuestionOutputFormatter;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;
import ru.otus.service.UserAnswerService;

import java.util.Optional;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {
    private static final String ENTER_ANSWER_TEXT = "Please, enter the number of option";

    private static final String OPTION_ANSWER_NOT_EXIST = "Such option doesn't exist, please try again";

    private final OutputService outputService;

    private final InputService inputService;

    private final QuestionOutputFormatter questionOutputFormatter;

    public UserAnswerServiceImpl(OutputService outputService,
                                 InputService inputService,
                                 QuestionOutputFormatter questionOutputFormatter){
        this.outputService = outputService;
        this.inputService = inputService;
        this.questionOutputFormatter = questionOutputFormatter;
    }

    @Override
    public Answer getUserAnswer(Question question) {
        outputService.output(questionOutputFormatter.format(question));
        while (true){
            outputService.output(ENTER_ANSWER_TEXT);
            String userAnswerText = inputService.read();
            try {
                int userAnswer = Integer.parseInt(userAnswerText);
                Optional<Answer> mayBeAnswer = Optional.ofNullable(question.getAnswer(userAnswer));
                return mayBeAnswer.orElseThrow(AnswerNotFoundException::new);
            } catch (Exception e){
                outputService.output(OPTION_ANSWER_NOT_EXIST);
            }
        }
    }
}
