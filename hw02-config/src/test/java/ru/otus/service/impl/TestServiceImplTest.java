package ru.otus.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.entity.TestResult;
import ru.otus.entity.User;
import ru.otus.service.TestService;
import ru.otus.service.UserAnswerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestServiceImplTest {


    @Test
    void testRightAnswersResult() {
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

        UserAnswerService mockedService = Mockito.mock(UserAnswerService.class);
        Mockito.when(mockedService.getUserAnswer(question)).thenReturn(answerTrue);

        TestService testService = new TestServiceImpl(mockedService);

        TestResult result = testService.test(user, questions);

        assertEquals(1, result.getRightAnswers());
        assertEquals(1, result.getQuestionCount());
        assertEquals("Test user 1", result.getUserName());
    }

    @Test
    void testDifferentAnswersResult() {
        List<Answer> answerOptions1 = new ArrayList<>();
        Answer answerTrue1 = new Answer("Test user answer 1", true);
        Answer answerFalse1 = new Answer("Test user answer 2", false);
        answerOptions1.add(answerTrue1);
        answerOptions1.add(answerFalse1);
        Question question1 = new Question("Test question text", answerOptions1);
        List<Answer> answerOptions2 = new ArrayList<>();
        Answer answerTrue2 = new Answer("Test user answer 1", true);
        Answer answerFalse2 = new Answer("Test user answer 2", false);
        answerOptions2.add(answerFalse2);
        answerOptions2.add(answerTrue2);
        Question question2 = new Question("Test question text 2", answerOptions2);
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        String userName = "Test user 2";
        User user = new User(userName);
        UserAnswerService mockedUserAnswerService = Mockito.mock(UserAnswerService.class);
        Mockito.when(mockedUserAnswerService.getUserAnswer(question1)).thenReturn(answerTrue1);
        Mockito.when(mockedUserAnswerService.getUserAnswer(question2)).thenReturn(answerFalse2);
        TestService testService = new TestServiceImpl(mockedUserAnswerService);
        TestResult result = testService.test(user, questions);
        assertEquals(1, result.getRightAnswers());
        assertEquals(2, result.getQuestionCount());
        assertEquals("Test user 2", result.getUserName());
    }
}