package ru.otus.entity;

import java.util.Map;
import java.util.TreeMap;

public class Question {
    private final String text;

    private final Map<Integer, Answer> answerOptions = new TreeMap<>();

    public Question(String text, Map<Integer, Answer> answerOptions){
        this.text = text;
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
