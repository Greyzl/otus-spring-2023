package ru.otus.mapper;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.jupiter.api.Test;
import ru.otus.entity.Question;
import ru.otus.entity.csv.QuestionCsv;

import static org.junit.jupiter.api.Assertions.*;

class QuestionMapperCsvTest {

    private final QuestionMapperCsv mapperCsv = new QuestionMapperCsv();

    @Test
    void test_mapQuestion_whiteSpaces() {
        QuestionCsv questionCsv_whiteSpaces = new QuestionCsv();
        questionCsv_whiteSpaces.setText(" Testing question  ");
        MultiValuedMap<String, String> testAnswerOptions = new HashSetValuedHashMap<>();
        testAnswerOptions.put(" parameter1", " placegolder  ");
        questionCsv_whiteSpaces.setAnswerOptions(testAnswerOptions);

        Question question = mapperCsv.mapQuestion(questionCsv_whiteSpaces);

        assertEquals("Testing question", question.getText());
        assertEquals("placegolder", question.getAnswerOptions().get(0));
    }
}