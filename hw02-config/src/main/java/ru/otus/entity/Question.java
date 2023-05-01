package ru.otus.entity;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Question {
    private final String text;

    private final Answer rightAnswer;

    private final Map<Integer, Answer> answerOptions = new TreeMap<>();

    public Question(String text,Answer rightAnswer, List<Answer> answerOptions){
        this.text = text;
        this.rightAnswer = rightAnswer;
        int position = 0;
        for (var answer: answerOptions){
            position++;
            this.answerOptions.put(position, answer);
        }
    }

    public String getText() {
        return text;
    }

    public Answer getRightAnswer() {
        return rightAnswer;
    }

    public Map<Integer, Answer> getAnswerOptions(){
        return answerOptions;
    }

    public Answer getAnswer(int position){
        return answerOptions.get(position);
    }
}
