package ru.otus.dao.impl;

import org.junit.jupiter.api.Test;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.mapper.QuestionMapperCsv;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class QuestionCSVDaoTest {

    @Test
    void loadQuestionsTest() {
        QuestionMapperCsv questionMapperCsv = new QuestionMapperCsv();
        QuestionCSVDao questionCSVDao = new QuestionCSVDao(questionMapperCsv, "testQuestions.csv");
        List<Question> questions = questionCSVDao.loadQuestions();
        Question question = questions.get(0);

        assertEquals("Test Question text", question.getText());
        List<Answer> answerOptionsList = question.getAnswerOptions();

        List<Answer> expectedAnswerResults = new ArrayList<>();
        Answer expectedAnswer1 = new Answer("right answer",true);
        Answer expectedAnswer2 = new Answer("wrong_answer_1",false);
        Answer expectedAnswer3 = new Answer("wrong_answer_2",false);

        expectedAnswerResults.add(expectedAnswer1);
        expectedAnswerResults.add(expectedAnswer2);
        expectedAnswerResults.add(expectedAnswer3);

        assertIterableEquals(expectedAnswerResults, answerOptionsList);

    }
}