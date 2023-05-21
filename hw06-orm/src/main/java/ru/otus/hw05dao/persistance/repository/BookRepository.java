package ru.otus.hw05dao.persistance.repository;

import ru.otus.hw05dao.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getAll();

    Optional<Book> getById(long id);

    Optional<Book> findByTitle(String name);

    Book save(Book book);

    void deleteById(long id);
}
