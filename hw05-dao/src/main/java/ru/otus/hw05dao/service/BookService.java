package ru.otus.hw05dao.service;

import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.exception.AuthorNotFoundException;
import ru.otus.hw05dao.exception.GenreNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAll();

    List<Book> findByAuthorName(String authorName) throws AuthorNotFoundException;

    List<Book> findByGenreName(String genreName) throws GenreNotFoundException;

    Optional<Book> get(long id) ;

    Optional<Book> findByTitle(String title) ;

    Book add(String title, String authorName, String genreName);

    Book update(Book book, String title, String authorName, String genreName);

    void deleteById(long id);
}
