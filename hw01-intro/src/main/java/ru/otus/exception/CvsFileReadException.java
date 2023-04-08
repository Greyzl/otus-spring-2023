package ru.otus.exception;

public class CvsFileReadException extends RuntimeException {

    public CvsFileReadException(Exception e) {
        super(e);
    }
}
