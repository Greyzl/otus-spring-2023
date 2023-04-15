package ru.otus.service.impl;

import ru.otus.dao.QueationDao;
import ru.otus.entity.Question;
import ru.otus.service.QuestionService;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final QueationDao queationDao;

    public QuestionServiceImpl(QueationDao queationDao){
        this.queationDao = queationDao;
    }

    @Override
    public List<Question> getQuestions() {
        return queationDao.loadQuestions();
    }
}
