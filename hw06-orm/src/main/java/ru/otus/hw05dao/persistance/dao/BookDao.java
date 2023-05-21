package ru.otus.hw05dao.persistance.dao;

import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    List<Book> getAll();

    List<Book> findByAuthor(Author author);

    List<Book> findByGenre(Genre genre);

    Optional<Book> getById(long id);

    Optional<Book> findByTitle(String name);

    Book insert(Book book);

    void update(Book book);

    void deleteById(long id);
}
