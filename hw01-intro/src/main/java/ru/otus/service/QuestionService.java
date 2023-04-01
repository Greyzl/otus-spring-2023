package ru.otus.service;

import ru.otus.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestions();
}
