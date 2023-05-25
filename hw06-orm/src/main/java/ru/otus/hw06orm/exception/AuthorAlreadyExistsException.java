package ru.otus.hw06orm.exception;

import ru.otus.hw06orm.entity.Author;

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
