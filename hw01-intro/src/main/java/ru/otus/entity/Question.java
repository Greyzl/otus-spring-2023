package ru.otus.entity;

import ru.otus.entity.enums.QuestionType;

public class Question {
    private String text;

    private QuestionType type;

    private String prompt;

    private String answer;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {

        return getText() + " " + getPrompt();
    }
}
