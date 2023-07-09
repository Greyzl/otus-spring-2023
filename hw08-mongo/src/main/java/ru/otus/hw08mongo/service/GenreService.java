package ru.otus.hw08mongo.service;

import ru.otus.hw08mongo.persistance.entity.Genre;
import ru.otus.hw08mongo.exception.GenreAlreadyExistsException;
import ru.otus.hw08mongo.exception.GenreNotFoundException;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre get(String id) throws GenreNotFoundException;

    Genre getByName(String name) throws GenreNotFoundException;

    Genre getOrCreate(String name);

    Genre add(String name) throws GenreAlreadyExistsException;

    Genre update(String id, String name) throws GenreNotFoundException;

    void delete(String id) throws GenreNotFoundException;
}
