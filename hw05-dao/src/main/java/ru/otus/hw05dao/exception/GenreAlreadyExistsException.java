package ru.otus.hw05dao.exception;

import ru.otus.hw05dao.entity.Genre;

public class GenreAlreadyExistsException extends RuntimeException{
    private final Genre genre;

    public GenreAlreadyExistsException(Genre genre) {
        super();
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }
}
