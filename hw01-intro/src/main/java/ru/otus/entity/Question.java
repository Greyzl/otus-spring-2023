package ru.otus.entity;


public class Question {
    private String text;

    private String prompt;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String toString() {

        return getText() + " " + getPrompt();
    }
}
