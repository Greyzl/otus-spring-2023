package ru.otus.dao;

import ru.otus.entity.Question;

import java.util.List;

public interface QueationDao {
    List<Question> loadQuestions();
}
