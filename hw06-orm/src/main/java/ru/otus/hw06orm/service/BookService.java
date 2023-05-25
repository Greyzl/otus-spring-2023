package ru.otus.hw06orm.service;

import ru.otus.hw06orm.entity.Book;
import ru.otus.hw06orm.entity.Comment;
import ru.otus.hw06orm.exception.AuthorNotFoundException;
import ru.otus.hw06orm.exception.GenreNotFoundException;

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

    void delete(Book book);

    void addComment(Book book, String commentText);

    Optional<Comment> getBookCommentByIndex(Book book, int commentIndex);

    List<Comment> getBookComments(Book book);

    void removeComment(Book book, Comment comment);
}
