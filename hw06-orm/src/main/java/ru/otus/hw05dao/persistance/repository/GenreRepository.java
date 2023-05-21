package ru.otus.hw05dao.persistance.repository;

import ru.otus.hw05dao.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> getAll();

    Optional<Genre> getById(long id);

    Optional<Genre> findByName(String name);

    Genre save(Genre genre);

    void deleteById(long id);
}
