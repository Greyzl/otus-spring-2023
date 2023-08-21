package ru.otus.hw08mongo.service;

import ru.otus.hw08mongo.dto.BookDto;
import ru.otus.hw08mongo.persistance.entity.Comment;
import ru.otus.hw08mongo.exception.AuthorNotFoundException;
import ru.otus.hw08mongo.exception.BookAlreadyExistsException;
import ru.otus.hw08mongo.exception.BookNotFoundException;
import ru.otus.hw08mongo.exception.CommentNotFoundException;
import ru.otus.hw08mongo.exception.GenreNotFoundException;

import java.util.List;

public interface BookService {

    List<BookDto> getAll();

    List<BookDto> findByAuthorName(String authorName) throws AuthorNotFoundException;

    List<BookDto> findByGenreName(String genreName) throws GenreNotFoundException;

    BookDto get(String id) throws BookNotFoundException;

    BookDto findByTitle(String title) throws BookNotFoundException;

    BookDto add(String title, String authorName, String genreName) throws BookAlreadyExistsException;

    BookDto update(String id, String title, String authorName, String genreName) throws BookNotFoundException;

    void delete(String id) throws BookNotFoundException;

    void addComment(String bookId, String commentText) throws BookNotFoundException;

    List<Comment> getBookComments(String bookId) throws BookNotFoundException;

    void removeComment(String bookId, String commentId) throws BookNotFoundException, CommentNotFoundException;
}
