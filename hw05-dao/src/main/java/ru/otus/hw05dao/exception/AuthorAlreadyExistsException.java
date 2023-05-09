package ru.otus.hw05dao.exception;

import ru.otus.hw05dao.entity.Author;

public class AuthorAlreadyExistsException extends RuntimeException{
    private final Author author;

    public AuthorAlreadyExistsException(Author author) {
        super();
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }
}
