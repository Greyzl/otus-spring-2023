package ru.otus.hw06orm.exception;

import ru.otus.hw06orm.persistance.entity.Genre;

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
