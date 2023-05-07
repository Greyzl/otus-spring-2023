package ru.otus.exception;

public class LoadQuestionsException extends RuntimeException {

    public LoadQuestionsException(Exception e) {
        super(e);
    }
}
