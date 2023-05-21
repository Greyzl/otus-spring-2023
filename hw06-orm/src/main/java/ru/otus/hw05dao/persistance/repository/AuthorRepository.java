package ru.otus.hw05dao.persistance.repository;

import ru.otus.hw05dao.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> getAll();

    Optional<Author> getById(long id);

    Optional<Author> findByName(String name);

    Author save(Author author);

    void deleteById(long id);
}

