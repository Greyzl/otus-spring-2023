package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.entity.Answer;
import ru.otus.exception.AnswerNotFoundException;
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

    public UserAnswerServiceImpl(OutputService outputService,
                                 InputService inputService){
        this.outputService = outputService;
        this.inputService = inputService;
    }

    @Override
    public Answer getUserAnswer(List<Answer> answerOptions) {
        while (true){
            outputService.output(ENTER_ANSWER_TEXT);
            String userAnswerText = inputService.read();
            try {
                int userAnswer = Integer.parseInt(userAnswerText);
                Optional<Answer> mayBeAnswer = answerOptions.stream()
                        .filter(answer -> answer.getPosition() == userAnswer).findFirst();
                return mayBeAnswer.orElseThrow(AnswerNotFoundException::new);
            } catch (Exception e){
                outputService.output(OPTION_ANSWER_NOT_EXIST);
            }
        }
    }
}
