package ru.otus.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum QuestionType {

    FREEFORMAT("freeformat");

    private final String type;

    QuestionType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Optional<QuestionType> getByType(String type){
        return Arrays.stream(QuestionType.values())
                .filter(questionType -> questionType.getType().equals(type))
                .findFirst();
    }
}
