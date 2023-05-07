package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.entity.PositionedAnswerOption;
import ru.otus.entity.PositionedAnswerOptions;
import ru.otus.exception.AnswerNotFoundException;
import ru.otus.formatter.GetUserAnswerTextFormatter;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;
import ru.otus.service.UserAnswerService;

import java.util.List;
import java.util.Optional;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {
    private static final String ENTER_ANSWER_TEXT = "Please, enter the number of option";

    private static final String OPTION_ANSWER_NOT_EXIST = "Such option doesn't exist, please try again";

    private final OutputService outputService;

    private final InputService inputService;

    private final GetUserAnswerTextFormatter getUserAnswerTextFormatter;

    public UserAnswerServiceImpl(OutputService outputService,
                                 InputService inputService,
                                 GetUserAnswerTextFormatter getUserAnswerTextFormatter){
        this.outputService = outputService;
        this.inputService = inputService;
        this.getUserAnswerTextFormatter = getUserAnswerTextFormatter;
    }

    @Override
    public Answer getUserAnswer(Question question) {
        List<Answer> answerOptions = question.getAnswerOptions();
        PositionedAnswerOptions positionedAnswerOptions = new PositionedAnswerOptions(answerOptions);
        outputService.output(getUserAnswerTextFormatter.format(question, positionedAnswerOptions));
        while (true){
            outputService.output(ENTER_ANSWER_TEXT);
            String userAnswerPositionText = inputService.read();
            try {
                int userAnswerPosition = Integer.parseInt(userAnswerPositionText);
                Optional<PositionedAnswerOption> mayBeUserAnswerOption =
                        positionedAnswerOptions.get(userAnswerPosition);
                return mayBeUserAnswerOption.orElseThrow(AnswerNotFoundException::new).getAnswer();
            } catch (Exception e){
                outputService.output(OPTION_ANSWER_NOT_EXIST);
            }
        }
    }
}
