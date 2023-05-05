package ru.otus.dao.impl;

import org.junit.jupiter.api.Test;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.mapper.QuestionMapperCsv;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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
        Answer expectedAnswer_1 = new Answer("right answer",true);
        Answer expectedAnswer_2 = new Answer("wrong_answer_1",false);
        Answer expectedAnswer_3 = new Answer("wrong_answer_2",false);

        expectedAnswerResults.add(expectedAnswer_1);
        expectedAnswerResults.add(expectedAnswer_2);
        expectedAnswerResults.add(expectedAnswer_3);

        assertIterableEquals(expectedAnswerResults, answerOptionsList);

    }
}