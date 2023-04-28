package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.entity.TestResult;
import ru.otus.entity.User;
import ru.otus.formatter.QuestionOutputFormatter;
import ru.otus.service.OutputService;
import ru.otus.service.TestService;
import ru.otus.service.UserAnswerService;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private final OutputService outputService;

    private final QuestionOutputFormatter questionOutputFormatter;

    private final UserAnswerService userAnswerService;

    public TestServiceImpl(OutputService outputService,
                           QuestionOutputFormatter questionOutputFormatter,
                           UserAnswerService userAnswerService){
        this.outputService = outputService;
        this.questionOutputFormatter = questionOutputFormatter;
        this.userAnswerService = userAnswerService;
    }

    @Override
    public TestResult test(User user, List<Question> questionList) {
        TestResult testResult = new TestResult(user);
        for (Question question: questionList){
            outputService.output(questionOutputFormatter.format(question));
            Answer answer = userAnswerService.getUserAnswer(question.getAnswerOptions());
            testResult.addAnswer(question, answer);
        }
        return testResult;
    }
}
