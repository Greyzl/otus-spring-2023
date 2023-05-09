package ru.otus.service.impl;

import org.junit.jupiter.api.Test;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.entity.TestResult;
import ru.otus.entity.User;
import ru.otus.service.TestService;
import ru.otus.service.UserAnswerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TestServiceImplTest {

    @Test
    void testRightAnswersResult() {
        UserAnswerService userAnswerService = (question) ->
                question.getAnswerOptions().stream().filter(Answer::getIsRight).findFirst().orElseThrow();
        TestService testService = new TestServiceImpl(userAnswerService);

        List<Answer> answerOptions = new ArrayList<>();
        Answer answerTrue = new Answer("Test user answer 1", true);
        Answer answerFalse = new Answer("Test user answer 2", false);
        answerOptions.add(answerTrue);
        answerOptions.add(answerFalse);

        List<Question> questions = new ArrayList<>();
        Question question = new Question("Test question text", answerOptions);
        questions.add(question);

        String userName = "Test user 1";
        User user = new User(userName);
        TestResult result = testService.test(user, questions);

        assertEquals(1, result.getRightAnswers());
        assertEquals(1, result.getQuestionCount());
        assertEquals("Test user 1", result.getUserName());
    }

    @Test
    void testDifferentAnswersResult() {
        UserAnswerService userAnswerService = (question) ->
                question.getAnswerOptions().stream().findFirst().orElseThrow();
        TestService testService = new TestServiceImpl(userAnswerService);

        List<Answer> answerOptions_1 = new ArrayList<>();
        Answer answerTrue_1 = new Answer("Test user answer 1", true);
        Answer answerFalse_1 = new Answer("Test user answer 2", false);
        answerOptions_1.add(answerTrue_1);
        answerOptions_1.add(answerFalse_1);

        Question question_1 = new Question("Test question text", answerOptions_1);

        List<Answer> answerOptions_2 = new ArrayList<>();
        Answer answerTrue_2 = new Answer("Test user answer 1", true);
        Answer answerFalse_2 = new Answer("Test user answer 2", false);
        answerOptions_2.add(answerFalse_2);
        answerOptions_2.add(answerTrue_2);

        Question question_2 = new Question("Test question text 2", answerOptions_2);

        List<Question> questions = new ArrayList<>();
        questions.add(question_1);
        questions.add(question_2);

        String userName = "Test user 2";
        User user = new User(userName);
        TestResult result = testService.test(user, questions);

        assertEquals(1, result.getRightAnswers());
        assertEquals(2, result.getQuestionCount());
        assertEquals("Test user 2", result.getUserName());
    }
}