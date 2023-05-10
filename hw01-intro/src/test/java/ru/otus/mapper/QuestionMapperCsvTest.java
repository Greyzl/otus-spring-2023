package ru.otus.mapper;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.jupiter.api.Test;
import ru.otus.entity.Question;
import ru.otus.entity.csv.QuestionCsv;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionMapperCsvTest {

    private final QuestionMapperCsv mapperCsv = new QuestionMapperCsv();

    @Test
    void testMapQuestionWhiteSpaces() {
        QuestionCsv questionCsvWhiteSpaces = new QuestionCsv();
        questionCsvWhiteSpaces.setText(" Testing question  ");
        questionCsvWhiteSpaces.setRightAnswer("placegolder     ");
        MultiValuedMap<String, String> testAnswerOptions = new HashSetValuedHashMap<>();
        testAnswerOptions.put(" parameter1", " placegolder  ");
        questionCsvWhiteSpaces.setAnswerOptions(testAnswerOptions);

        Question question = mapperCsv.mapQuestion(questionCsvWhiteSpaces);

        assertEquals("Testing question", question.getText());
        assertEquals("placegolder", question.getAnswerOptions().get(0));
        assertEquals("placegolder", question.getRightAnswer());
    }
}