package ru.otus.hw05dao.dao;

import ru.otus.hw05dao.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    List<Author> getAll();

    Optional<Author> getById(long id);

    Optional<Author> findByName(String name);

    Author insert(Author author);

    void update(Author author);

    void deleteById(long id);
}
