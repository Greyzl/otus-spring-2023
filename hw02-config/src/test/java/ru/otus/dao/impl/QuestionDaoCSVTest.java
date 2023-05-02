package ru.otus.dao.impl;

import org.junit.jupiter.api.Test;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.mapper.QuestionMapperCsv;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoCSVTest {

    @Test
    void loadQuestionsTest() {
        QuestionMapperCsv questionMapperCsv = new QuestionMapperCsv();
        QuestionDaoCSV questionDaoCSV = new QuestionDaoCSV(questionMapperCsv, "testQuestions.csv");
        List<Question> questions = questionDaoCSV.loadQuestions();
        Question question = questions.get(0);

        assertEquals("Test Question text", question.getText());
        Map<Integer, Answer> answerOptionsMap = question.getAnswerOptions();
        Set<Integer> keys = answerOptionsMap.keySet();
        assertTrue(keys.containsAll(List.of(1, 2, 3)));

        List<Answer> answerOptionsList = answerOptionsMap.values().stream().toList();

        Optional<Answer> mayBeRightAnswer = answerOptionsList.stream().filter(
                answer -> "right answer".equals(answer.getText())).findFirst();
        assertTrue(mayBeRightAnswer.isPresent());
        Answer rightAnswer = mayBeRightAnswer.get();
        assertTrue(rightAnswer.getIsRight());

        Optional<Answer> mayBeAnswerOne = answerOptionsList.stream().filter(
                answer -> "wrong_answer_1".equals(answer.getText())).findFirst();
        assertTrue(mayBeAnswerOne.isPresent());
        Answer answerOne = mayBeAnswerOne.get();
        assertFalse(answerOne.getIsRight());

        Optional<Answer> mayBeAnswerTwo = answerOptionsList.stream().filter(
                answer -> "wrong_answer_2".equals(answer.getText())).findFirst();
        assertTrue(mayBeAnswerTwo.isPresent());
        Answer answerTwo = mayBeAnswerTwo.get();
        assertFalse(answerTwo.getIsRight());
    }
}