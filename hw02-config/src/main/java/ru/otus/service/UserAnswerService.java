package ru.otus.service;

import ru.otus.entity.Answer;
import ru.otus.entity.Question;

public interface UserAnswerService {
    Answer getUserAnswer(Question question);
}
