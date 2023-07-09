package ru.otus.hw08mongo.service;

import ru.otus.hw08mongo.persistance.entity.Author;
import ru.otus.hw08mongo.exception.AuthorAlreadyExistsException;
import ru.otus.hw08mongo.exception.AuthorNotFoundException;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author get(String id) throws AuthorNotFoundException;

    Author findByName(String name) throws AuthorNotFoundException;

    Author getOrCreate(String name);

    Author add(String authorName) throws AuthorAlreadyExistsException;

    Author update(String id, String authorName) throws AuthorNotFoundException;

    void delete(String id) throws AuthorNotFoundException;
}
