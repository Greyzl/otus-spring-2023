package ru.otus.entity;

import java.util.Objects;

public class Answer {

    private final String text;

    private final boolean isRight;

    public Answer(String text, boolean isRight){
        this.text = text;
        this.isRight = isRight;
    }

    public String getText() {
        return text;
    }

    public boolean getIsRight(){
        return isRight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer answer)) {
            return false;
        }
        return Objects.equals(text, answer.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
