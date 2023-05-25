package ru.otus.hw06orm.exception;

import ru.otus.hw06orm.entity.Book;

public class BookAlreadyExistsException extends RuntimeException{
    private final Book book;

    public BookAlreadyExistsException(Book book) {
        super();
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
