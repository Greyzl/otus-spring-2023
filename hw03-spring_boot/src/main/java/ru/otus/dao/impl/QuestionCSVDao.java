package ru.otus.dao.impl;


import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import ru.otus.config.QuestionCsvResourceFileNameHolder;
import ru.otus.dao.QueationDao;
import ru.otus.entity.Question;
import ru.otus.dto.QuestionCsvDto;
import ru.otus.exception.LoadQuestionsException;
import ru.otus.exception.QuestionMapException;
import ru.otus.mapper.QuestionMapperCsv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionCSVDao implements QueationDao {

    private final QuestionMapperCsv questionMapperCsv;

    private final String fileName ;

    public QuestionCSVDao(QuestionMapperCsv questionMapperCsv, QuestionCsvResourceFileNameHolder holder){
        this.questionMapperCsv = questionMapperCsv;
        this.fileName = holder.getResourceFileName();
    }

    public List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        try (InputStreamReader streamReader = new InputStreamReader(
                new ClassPathResource(fileName).getInputStream())) {
            List<QuestionCsvDto> questionsCsv = new CsvToBeanBuilder<QuestionCsvDto>(streamReader)
                    .withType(QuestionCsvDto.class).build()
                    .parse();
            questionsCsv.forEach(questionCsvDto ->
                    questions.add(questionMapperCsv.mapQuestion(questionCsvDto)));
        } catch (IllegalStateException | IOException | QuestionMapException e){
            throw new LoadQuestionsException(e);
        }
        return questions;
    }
}



