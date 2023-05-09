package ru.otus.hw05dao.service;

import ru.otus.hw05dao.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Genre> getAll();

    Optional<Genre> get(long id);

    Optional<Genre> getByName(String name);

    Genre getOrCreate(String name);

    Genre add(String name);

    Genre update(Genre genre, String name);

    void deleteById(long id);
}
