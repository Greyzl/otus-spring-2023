package ru.otus.entity;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final String text;

    private final String rightAnswer;

    private final List<String> answerOptions;

    public Question(String text){
        this.text = text;
        answerOptions = new ArrayList<>();
        rightAnswer = "";
    }

    public Question(String text,String rightAnswer, List<String> answerOptions){
        this.text = text;
        this.rightAnswer = rightAnswer;
        this.answerOptions = answerOptions;
    }

    public String getText() {
        return text;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public List<String> getAnswerOptions(){
        return answerOptions;
    }
}
