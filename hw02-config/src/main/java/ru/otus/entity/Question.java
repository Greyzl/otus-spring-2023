package ru.otus.entity;

import java.util.Map;

public class Question {
    private final String text;

    private final Map<Integer, Answer> answerOptions;

    public Question(String text, Map<Integer, Answer> answerOptions){
        this.text = text;
        this.answerOptions = answerOptions;
    }

    public String getText() {
        return text;
    }

    public Map<Integer, Answer> getAnswerOptions(){
        return answerOptions;
    }

    public Answer getAnswer(int position){
        return answerOptions.get(position);
    }
}
