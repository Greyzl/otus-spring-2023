package ru.otus.hw05dao.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw05dao.dao.BookDao;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.entity.Genre;
import ru.otus.hw05dao.exception.FailedToInsertBookException;
import ru.otus.hw05dao.mapper.BookMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(
                "SELECT b.ID AS BOOK_ID, b.TITLE as BOOK_TITLE, "
                        + "a.ID AS AUTHOR_ID, a.NAME AS AUTHOR_NAME, "
                        + "g.ID AS GENRE_ID, g.NAME AS GENRE_NAME "
                        + "FROM BOOKS b INNER JOIN AUTHORS a on b.AUTHOR_ID = a.ID "
                        + "INNER JOIN GENRES g ON b.GENRE_ID = g.ID", new BookMapper());
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("authorId", author.getId());
        return namedParameterJdbcOperations.query(
                "SELECT b.ID AS BOOK_ID, b.TITLE as BOOK_TITLE, "
                        + "a.ID AS AUTHOR_ID, a.NAME AS AUTHOR_NAME, "
                        + "g.ID AS GENRE_ID, g.NAME AS GENRE_NAME "
                        + "FROM BOOKS b INNER JOIN AUTHORS a on b.AUTHOR_ID = a.ID "
                        + "INNER JOIN GENRES g ON b.GENRE_ID = g.ID "
                        + "WHERE AUTHOR_ID = :authorId",mapSqlParameterSource, new BookMapper());
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("genreId", genre.getId());
        return namedParameterJdbcOperations.query(
                "SELECT b.ID AS BOOK_ID, b.TITLE as BOOK_TITLE, "
                        + "a.ID AS AUTHOR_ID, a.NAME AS AUTHOR_NAME, "
                        + "g.ID AS GENRE_ID, g.NAME AS GENRE_NAME "
                        + "FROM BOOKS b INNER JOIN AUTHORS a on b.AUTHOR_ID = a.ID "
                        + "INNER JOIN GENRES g ON b.GENRE_ID = g.ID "
                        + "WHERE GENRE_ID = :genreId",mapSqlParameterSource, new BookMapper());
    }

    @Override
    public Optional<Book> getById(long id) {
        return namedParameterJdbcOperations.query(
                "SELECT b.ID AS BOOK_ID, b.TITLE as BOOK_TITLE, "
                        + "a.ID AS AUTHOR_ID, a.NAME AS AUTHOR_NAME, "
                        + "g.ID AS GENRE_ID, g.NAME AS GENRE_NAME "
                        + "FROM BOOKS b INNER JOIN AUTHORS a on b.AUTHOR_ID = a.ID "
                        + "INNER JOIN GENRES g ON b.GENRE_ID = g.ID WHERE b.ID = :id",
                Map.of("id", id), new BookMapper()).stream().findFirst();
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return namedParameterJdbcOperations.query(
                "SELECT b.ID AS BOOK_ID, b.TITLE as BOOK_TITLE, "
                        + "a.ID AS AUTHOR_ID, a.NAME AS AUTHOR_NAME, "
                        + "g.ID AS GENRE_ID, g.NAME AS GENRE_NAME "
                        + "FROM BOOKS b INNER JOIN AUTHORS a on b.AUTHOR_ID = a.ID "
                        + "INNER JOIN GENRES g ON b.GENRE_ID = g.ID WHERE b.TITLE = :title",
                Map.of("title", title), new BookMapper()).stream().findFirst();
    }

    @Override
    public Book insert(Book book) {
        String title = book.getTitle();
        long authorId = book.getAuthorId();
        long genreId = book.getGenreId();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("title", title);
        mapSqlParameterSource.addValue("authorId", authorId);
        mapSqlParameterSource.addValue("genreId", genreId);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "INSERT INTO BOOKS (TITLE, AUTHOR_ID, GENRE_ID) VALUES (:title, :authorId, :genreId)",
                mapSqlParameterSource, keyHolder, new String[]{"ID"});
        Number key = Optional.ofNullable(keyHolder.getKey())
                .orElseThrow(FailedToInsertBookException::new);
        long id = key.longValue();
        return book.toBuilder().setId(id).build();
    }

    @Override
    public void update(Book book) {
        long bookId = book.getId();
        String title = book.getTitle();
        long authorId = book.getAuthorId();
        long genreId = book.getGenreId();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("bookId", bookId);
        mapSqlParameterSource.addValue("title", title);
        mapSqlParameterSource.addValue("authorId", authorId);
        mapSqlParameterSource.addValue("genreId", genreId);

        namedParameterJdbcOperations.update(
                "UPDATE BOOKS SET TITLE = :title, AUTHOR_ID = :authorId, GENRE_ID = :genreId "
                        + "WHERE ID = :bookId", mapSqlParameterSource);

    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update(
                "DELETE FROM BOOKS WHERE ID = :id", Map.of("id", id));
    }
}
