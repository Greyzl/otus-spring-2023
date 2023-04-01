package ru.otus.mapper;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.jupiter.api.Test;
import ru.otus.entity.Question;
import ru.otus.entity.csv.QuestionCsv;
import ru.otus.entity.enums.QuestionType;

import static org.junit.jupiter.api.Assertions.*;

class QuestionMapperCsvTest {

    private QuestionMapperCsv mapperCsv = new QuestionMapperCsv();

    @Test
    void test_mapQuestion_whiteSpaces() {
        QuestionCsv questionCsv_whiteSpaces = new QuestionCsv();
        questionCsv_whiteSpaces.setText(" Testing question  ");
        questionCsv_whiteSpaces.setType(" freeformat  ");
        MultiValuedMap<String, String> testArgs = new HashSetValuedHashMap<>();
        testArgs.put(" parameter1", " placegolder  ");
        questionCsv_whiteSpaces.setArgs(testArgs);

        Question question = mapperCsv.mapQuestion(questionCsv_whiteSpaces);

        assertEquals("Testing question", question.getText());
        assertEquals(QuestionType.FREEFORMAT, question.getType());
        assertEquals("placegolder", question.getPrompt());
    }
}