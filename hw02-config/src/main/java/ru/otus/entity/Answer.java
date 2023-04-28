package ru.otus.entity;

import java.util.Objects;

public class Answer {

    private final int position;

    private final String text;

    public Answer(int position, String text){
        this.position = position;
        this.text = text;
    }

    public int getPosition(){
        return position;
    }


    public String getText() {
        return text;
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
