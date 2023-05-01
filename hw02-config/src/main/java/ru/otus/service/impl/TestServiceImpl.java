package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.entity.TestResult;
import ru.otus.entity.User;
import ru.otus.service.TestService;
import ru.otus.service.UserAnswerService;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private final UserAnswerService userAnswerService;

    public TestServiceImpl(UserAnswerService userAnswerService){
        this.userAnswerService = userAnswerService;
    }

    @Override
    public TestResult test(User user, List<Question> questionList) {
        TestResult testResult = new TestResult(user);
        for (Question question: questionList){
            Answer answer = userAnswerService.getUserAnswer(question);
            testResult.addAnswer(question, answer);
        }
        return testResult;
    }
}
