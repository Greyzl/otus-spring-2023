package ru.otus.service.imp;

import ru.otus.dao.QueationDao;
import ru.otus.entity.Question;
import ru.otus.service.QuestionService;

import java.util.List;

public class QuestionServiceImp implements QuestionService {
    private QueationDao queationDao;

    public QuestionServiceImp(QueationDao queationDao){
        this.queationDao = queationDao;
    }

    @Override
    public List<Question> getQuestions() {
        return queationDao.loadQuestions();
    }
}
