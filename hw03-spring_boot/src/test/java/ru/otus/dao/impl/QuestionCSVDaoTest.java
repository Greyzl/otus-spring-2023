package ru.otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest
class QuestionCSVDaoTest {

    @Autowired
    private QuestionCSVDao questionCSVDao;

    @Test
    void loadQuestionsTest() {
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