package ru.otus.hw05dao.exception;

import ru.otus.hw05dao.entity.Book;

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
