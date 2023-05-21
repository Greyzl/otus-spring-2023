package ru.otus.hw05dao.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.entity.Genre;
import ru.otus.hw05dao.persistance.dao.impl.BookDaoJdbc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDao;

    @Test
    void whenGetAllThenReturnAllBooks(){
        Author author1 = new Author(1, "test author 1");
        Author author2 = new Author(2, "test author 2");
        Author author3 = new Author(3, "test author 3");
        Author author4 = new Author(4, "test author 4");
        Genre genre1 = new Genre(1,"test genre 1");
        Genre genre2 = new Genre(2,"test genre 2");
        Book book1 = new Book(1, "test title book 1", author1, genre1);
        Book book2 = new Book(2, "test title book 2", author2, genre1);
        Book book3 = new Book(3, "test title book 3", author2, genre2);
        Book book4 = new Book(4, "test title book 4", author3, genre2);
        Book book5 = new Book(5, "test title book 5", author4, genre2);
        Book book6 = new Book(6, "test title book 6", author3, genre1);
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);

        List<Book> bookList = bookDao.getAll();
        assertIterableEquals(books, bookList);
    }

    @Test
    void givenAuthorWhenFindByAuthorThenReturnAuthorBooks() {
        Author author2 = new Author(2, "test author 2");

        Genre genre1 = new Genre(1,"test genre 1");
        Genre genre2 = new Genre(2,"test genre 2");

        Book book2 = new Book(2, "test title book 2", author2, genre1);
        Book book3 = new Book(3, "test title book 3", author2, genre2);

        List<Book> books = new ArrayList<>();
        books.add(book2);
        books.add(book3);

        List<Book> bookList = bookDao.findByAuthor(author2);
        assertIterableEquals(books, bookList);
    }

    @Test
    void givenFakeAuthorWhenFindByAuthorThenReturnEmptyList() {
        Author author8 = new Author(8, "test author 8");

        List<Book> bookList = bookDao.findByAuthor(author8);
        assertTrue(bookList.isEmpty());
    }

    @Test
    void givenGenreWhenFindByGenreThenReturnGenreBooks() {
        Genre genre1 = new Genre(1,"test genre 1");

        Author author1 = new Author(1, "test author 1");
        Author author2 = new Author(2, "test author 2");
        Author author3 = new Author(3, "test author 3");

        Book book1 = new Book(1, "test title book 1", author1, genre1);
        Book book2 = new Book(2, "test title book 2", author2, genre1);
        Book book6 = new Book(6, "test title book 6", author3, genre1);


        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book6);

        List<Book> bookList = bookDao.findByGenre(genre1);
        assertIterableEquals(books, bookList);
    }

    @Test
    void givenFakeGenreWhenFindByGenreThenReturnEmptyList() {
        Author fakeGenre = new Author(999, "test author 999");

        List<Book> bookList = bookDao.findByAuthor(fakeGenre);
        assertTrue(bookList.isEmpty());
    }

    @Test
    void givenIdWhenGetByIdThenReturnBook() {
        Genre genre1 = new Genre(1,"test genre 1");
        Author author1 = new Author(1, "test author 1");
        Book book1 = new Book(1, "test title book 1", author1, genre1);

        Book resultBook = bookDao.getById(1).orElseThrow();
        assertEquals(book1, resultBook);
    }

    @Test
    void givenTitleWhenFindByTitleThenReturnBook() {
        Genre genre1 = new Genre(1,"test genre 1");
        Author author2 = new Author(2, "test author 2");
        Book book2 = new Book(2, "test title book 2", author2, genre1);

        Book resultBook = bookDao.findByTitle("test title book 2").orElseThrow();
        assertEquals(book2, resultBook);
    }

    @Test
    void givenNewBookWhenInsertThenReturnBookWithId() {
        Genre genre1 = new Genre(1,"test genre 1");
        Author author2 = new Author(2, "test author 2");
        Book newBook = new Book("new Test book title", author2, genre1);
        Book exptectedBook = new Book(7, "new Test book title", author2, genre1);

        Book insertedBook = bookDao.insert(newBook);
        assertEquals(exptectedBook, insertedBook);

        Book findedBook = bookDao.getById(7).orElseThrow();
        assertEquals(exptectedBook, findedBook);
    }

    @Test
    void givenNewBookWhenUpdateThenSaved() {
        Author author2 = new Author(2, "test author 2");
        Genre genre2 = new Genre(2,"test genre 2");
        Book newBook = new Book(1, "new book title", author2, genre2);

        bookDao.update(newBook);

        Book resultedBook = bookDao.getById(1).orElseThrow();

        assertEquals(resultedBook, newBook);

    }

    @Test
    void givenIdWhenDeleteThenDeleted() {
        bookDao.deleteById(1);
        assertTrue(bookDao.getById(1).isEmpty());
    }
}