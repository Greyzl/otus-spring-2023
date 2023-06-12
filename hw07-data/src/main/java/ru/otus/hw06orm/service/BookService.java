package ru.otus.hw06orm.service;

import ru.otus.hw06orm.dto.BookDto;
import ru.otus.hw06orm.persistance.entity.Comment;
import ru.otus.hw06orm.exception.AuthorNotFoundException;
import ru.otus.hw06orm.exception.BookAlreadyExistsException;
import ru.otus.hw06orm.exception.BookNotFoundException;
import ru.otus.hw06orm.exception.CommentNotFoundException;
import ru.otus.hw06orm.exception.GenreNotFoundException;

import java.util.List;

public interface BookService {

    List<BookDto> getAll();

    List<BookDto> findByAuthorName(String authorName) throws AuthorNotFoundException;

    List<BookDto> findByGenreName(String genreName) throws GenreNotFoundException;

    BookDto get(long id) throws BookNotFoundException;

    BookDto findByTitle(String title) throws BookNotFoundException;

    BookDto add(String title, String authorName, String genreName) throws BookAlreadyExistsException;

    BookDto update(long id, String title, String authorName, String genreName) throws BookNotFoundException;

    void delete(long id) throws BookNotFoundException;

    void addComment(long bookId, String commentText) throws BookNotFoundException;

    List<Comment> getBookComments(long bookId) throws BookNotFoundException;

    void removeComment(long bookId, long commentId) throws BookNotFoundException, CommentNotFoundException;
}
