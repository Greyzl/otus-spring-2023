package ru.otus.hw06orm.service;

import ru.otus.hw06orm.entity.Genre;
import ru.otus.hw06orm.exception.GenreAlreadyExistsException;
import ru.otus.hw06orm.exception.GenreNotFoundException;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre get(long id) throws GenreNotFoundException;

    Genre getByName(String name) throws GenreNotFoundException;

    Genre getOrCreate(String name);

    Genre add(String name) throws GenreAlreadyExistsException;

    Genre update(long id, String name) throws GenreNotFoundException;

    void delete(long id) throws GenreNotFoundException;
}
