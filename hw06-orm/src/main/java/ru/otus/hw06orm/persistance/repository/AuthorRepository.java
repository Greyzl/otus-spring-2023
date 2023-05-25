package ru.otus.hw06orm.persistance.repository;

import ru.otus.hw06orm.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> getAll();

    Optional<Author> findById(long id);

    Optional<Author> findByName(String name);

    Author save(Author author);

    void delete(Author author);
}

