package ru.otus.hw05dao.dao;

import ru.otus.hw05dao.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> getAll();

    Optional<Genre> getById(long id);

    Optional<Genre> findByName(String name);

    Genre insert(Genre author);

    void update(Genre author);

    void deleteById(long id);
}
