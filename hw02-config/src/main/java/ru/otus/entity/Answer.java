package ru.otus.entity;

import java.util.Objects;

public class Answer {

    private final int position;

    private final String text;

    private final boolean isRight;

    public Answer(int position, String text, boolean isRight){
        this.position = position;
        this.text = text;
        this.isRight = isRight;
    }

    public int getPosition(){
        return position;
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
