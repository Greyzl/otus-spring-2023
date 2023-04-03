package ru.otus.exception.csv;

public class CvsFileReadException extends RuntimeException {

    public CvsFileReadException(Exception e) { super(e); }
}
