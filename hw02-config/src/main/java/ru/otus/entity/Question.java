package ru.otus.entity;

import java.util.List;

public class Question {
    private final String text;

    private final Answer rightAnswer;

    private final List<Answer> answerOptions;

    public Question(String text,Answer rightAnswer, List<Answer> answerOptions){
        this.text = text;
        this.rightAnswer = rightAnswer;

        this.answerOptions = answerOptions;
    }

    public String getText() {
        return text;
    }

    public Answer getRightAnswer() {
        return rightAnswer;
    }

    public List<Answer> getAnswerOptions(){
        return answerOptions;
    }
}
