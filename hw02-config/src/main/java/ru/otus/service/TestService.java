package ru.otus.service;

import ru.otus.entity.Question;
import ru.otus.entity.TestResult;
import ru.otus.entity.User;

import java.util.List;

public interface TestService {

    TestResult test(User user, List<Question> questionList);
}
