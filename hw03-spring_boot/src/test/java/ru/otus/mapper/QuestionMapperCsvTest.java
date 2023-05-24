package ru.otus.mapper;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.dto.QuestionCsvDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QuestionMapperCsvTest {

    @Autowired
    private QuestionMapperCsv mapperCsv;

    @Test
    void testMapQuestionWhiteSpaces() {
        QuestionCsvDto questionCsvDtoWhiteSpaces = new QuestionCsvDto();
        questionCsvDtoWhiteSpaces.setText(" Testing question  ");
        questionCsvDtoWhiteSpaces.setRightAnswer("rightOption     ");
        MultiValuedMap<String, String> testAnswerOptions = new HashSetValuedHashMap<>();
        testAnswerOptions.put(" parameter1", " wrongOption  ");
        questionCsvDtoWhiteSpaces.setAnswerOptions(testAnswerOptions);

        Question question = mapperCsv.mapQuestion(questionCsvDtoWhiteSpaces);
        assertEquals("Testing question", question.getText());

        Optional<Answer> mayBeRight = question.getAnswerOptions()
                .stream().filter(Answer::getIsRight).findFirst();
        Optional<Answer> mayBeWrong = question.getAnswerOptions()
                .stream().filter(answer -> !answer.getIsRight()).findFirst();

        assertEquals("rightOption", mayBeRight.orElseThrow().getText());
        assertEquals("wrongOption", mayBeWrong.orElseThrow().getText());
    }
}