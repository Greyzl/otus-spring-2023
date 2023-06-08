package ru.otus.hw06orm.service;

import ru.otus.hw06orm.persistance.entity.Author;
import ru.otus.hw06orm.exception.AuthorAlreadyExistsException;
import ru.otus.hw06orm.exception.AuthorNotFoundException;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author get(long id) throws AuthorNotFoundException;

    Author findByName(String name) throws AuthorNotFoundException;

    Author getOrCreate(String name);

    Author add(String authorName) throws AuthorAlreadyExistsException;

    Author update(long id, String authorName) throws AuthorNotFoundException;

    void delete(long id) throws AuthorNotFoundException;
}
