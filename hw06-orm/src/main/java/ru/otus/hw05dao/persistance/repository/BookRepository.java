package ru.otus.hw05dao.persistance.repository;

import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getAll();

    List<Book> getByAuthor(Author author);

    List<Book> getByGenre(Genre genre);

    Optional<Book> getById(long id);

    Optional<Book> findByTitle(String name);

    Book save(Book book);

    void delete(Book book);
}
