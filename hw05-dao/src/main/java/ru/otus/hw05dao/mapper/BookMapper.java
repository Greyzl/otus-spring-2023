package ru.otus.hw05dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long bookId = rs.getLong("BOOK_ID");
        String bookTitle = rs.getString("BOOK_TITLE");
        long authorId = rs.getLong("AUTHOR_ID");
        String authorName = rs.getString("AUTHOR_NAME");
        long genreId = rs.getLong("GENRE_ID");
        String genreName = rs.getString("GENRE_NAME");
        Author author = new Author(authorId, authorName);
        Genre genre = new Genre(genreId, genreName);
        return new Book(bookId, bookTitle, author, genre);
    }
}
