package ru.otus.dao.imp;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import ru.otus.dao.QueationDao;
import ru.otus.entity.Question;
import ru.otus.entity.csv.QuestionCsv;
import ru.otus.mapper.QuestionMapperCsv;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoCSV implements QueationDao {

    private QuestionMapperCsv questionMapperCsv;

    private String fileName ;

    public QuestionDaoCSV(){

    }

    public void setQuestionMapperCsv(QuestionMapperCsv questionMapperCsv) {
        this.questionMapperCsv = questionMapperCsv;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        try {
            InputStreamReader streamReader = new InputStreamReader(
                    new ClassPathResource(fileName).getInputStream());
            List<QuestionCsv> questionsCsv = new CsvToBeanBuilder<QuestionCsv>(streamReader)
                    .withType(QuestionCsv.class).build()
                    .parse();

            questionsCsv.forEach(questionCsv ->
                    questions.add(questionMapperCsv.mapQuestion(questionCsv)));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return questions;
    }
}



