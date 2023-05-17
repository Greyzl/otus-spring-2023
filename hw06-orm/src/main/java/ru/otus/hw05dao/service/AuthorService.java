package ru.otus.hw05dao.service;

import ru.otus.hw05dao.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> getAll();

    Optional<Author> get(long id) ;

    Optional<Author> findByName(String name) ;

    Author getOrCreate(String name);

    Author add(String authorName);

    Author update(Author author, String authorName);

    void deleteById(long id);
}
