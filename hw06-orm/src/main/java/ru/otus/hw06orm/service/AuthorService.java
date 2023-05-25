package ru.otus.hw06orm.service;

import ru.otus.hw06orm.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> getAll();

    Optional<Author> get(long id) ;

    Optional<Author> findByName(String name) ;

    Author getOrCreate(String name);

    Author add(String authorName);

    Author update(Author author, String authorName);

    void delete(Author author);
}
