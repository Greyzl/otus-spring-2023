package ru.otus.service.imp;

import ru.otus.dao.QueationDao;
import ru.otus.entity.Question;
import ru.otus.service.OutputService;
import ru.otus.service.QuestionService;

import java.util.List;

public class QuestionServiceImp implements QuestionService {
    private final QueationDao queationDao;

    private final OutputService outputService;

    public QuestionServiceImp(QueationDao queationDao,
                              OutputService outputService){
        this.queationDao = queationDao;
        this.outputService = outputService;
    }

    @Override
    public List<Question> getQuestions() {
        return queationDao.loadQuestions();
    }

    @Override
    public void showQuestions() {
        outputService.output(getQuestions());
    }
}
