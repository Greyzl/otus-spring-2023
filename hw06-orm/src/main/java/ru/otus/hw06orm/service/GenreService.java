package ru.otus.hw06orm.service;

import ru.otus.hw06orm.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Genre> getAll();

    Optional<Genre> get(long id);

    Optional<Genre> getByName(String name);

    Genre getOrCreate(String name);

    Genre add(String name);

    Genre update(Genre genre, String name);

    void delete(Genre genre);
}
