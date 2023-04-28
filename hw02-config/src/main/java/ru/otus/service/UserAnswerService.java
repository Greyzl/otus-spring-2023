package ru.otus.service;

import ru.otus.entity.Answer;

import java.util.List;

public interface UserAnswerService {
    Answer getUserAnswer(List<Answer> answerOptions);
}
