package ru.otus.entity;

import java.util.HashMap;
import java.util.Map;

public class TestResult {

    private final User user;

    private int rightAnswers = 0;

    private int questionCount = 0;

    private final Map<Question, Answer> answers = new HashMap<>();

    public TestResult(User user){
        this.user = user;
    }

    public String getUserName() {
        return user.getName();
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void addAnswer(Question question, Answer answer){
        answers.put(question, answer);
        questionCount ++;
        if (answer.getIsRight()){
            rightAnswers++;
        }
    }
}
