package ru.otus.service.impl;

import ru.otus.entity.Question;
import ru.otus.service.UserQuestionService;

public class UserQuestionServiceImpl implements UserQuestionService {

    private static final String USER_NAME_QUESTION_TEXT = "What is your name?";

    @Override
    public Question getUserNameQuestion() {
        return new Question(USER_NAME_QUESTION_TEXT);
    }
}
