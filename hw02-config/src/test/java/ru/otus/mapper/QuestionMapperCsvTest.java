package ru.otus.mapper;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.jupiter.api.Test;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.dto.QuestionCsvDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class QuestionMapperCsvTest {

    private final QuestionMapperCsv mapperCsv = new QuestionMapperCsv();

    @Test
    void test_mapQuestion_whiteSpaces() {
        QuestionCsvDto questionCsv_Dto_whiteSpaces = new QuestionCsvDto();
        questionCsv_Dto_whiteSpaces.setText(" Testing question  ");
        questionCsv_Dto_whiteSpaces.setRightAnswer("rightOption     ");
        MultiValuedMap<String, String> testAnswerOptions = new HashSetValuedHashMap<>();
        testAnswerOptions.put(" parameter1", " wrongOption  ");
        questionCsv_Dto_whiteSpaces.setAnswerOptions(testAnswerOptions);

        Question question = mapperCsv.mapQuestion(questionCsv_Dto_whiteSpaces);
        assertEquals("Testing question", question.getText());

        Optional<Answer> mayBeRight = question.getAnswerOptions()
                .stream().filter(Answer::getIsRight).findFirst();
        Optional<Answer> mayBeWrong = question.getAnswerOptions()
                .stream().filter(answer -> !answer.getIsRight()).findFirst();

        assertEquals("rightOption", mayBeRight.orElseThrow().getText());
        assertEquals("wrongOption", mayBeWrong.orElseThrow().getText());
    }
}