package ru.otus.hw05dao.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.entity.Genre;
import ru.otus.hw05dao.persistance.repository.jpa.BookRepositoryJPA;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(BookRepositoryJPA.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJPA bookRepositoryJPA;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void whenGetAllThenReturnAllBooks(){
        Author author1 = new Author(1, "test author 1");
        Author author2 = new Author(2, "test author 2");
        Author author3 = new Author(3, "test author 3");
        Author author4 = new Author(4, "test author 4");
        Genre genre1 = new Genre(1,"test genre 1");
        Genre genre2 = new Genre(2,"test genre 2");
        Book book1 = new Book(1, "test title book 1", author1, genre1, new ArrayList<>());
        Book book2 = new Book(2, "test title book 2", author2, genre1, new ArrayList<>());
        Book book3 = new Book(3, "test title book 3", author2, genre2, new ArrayList<>());
        Book book4 = new Book(4, "test title book 4", author3, genre2, new ArrayList<>());
        Book book5 = new Book(5, "test title book 5", author4, genre2, new ArrayList<>());
        Book book6 = new Book(6, "test title book 6", author3, genre1, new ArrayList<>());
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);

        List<Book> bookList = bookRepositoryJPA.getAll();
        for (Book book: bookList){
            System.out.println(book);
        }
        assertIterableEquals(books, bookList);
    }


    @Test
    void givenIdWhenGetByIdThenReturnBook() {
        Genre genre1 = new Genre(1,"test genre 1");
        Author author1 = new Author(1, "test author 1");
        Book book1 = new Book(1, "test title book 1", author1, genre1, new ArrayList<>());

        Book resultBook = bookRepositoryJPA.getById(1).orElseThrow();
        assertEquals(book1, resultBook);
    }

    @Test
    void givenTitleWhenFindByTitleThenReturnBook() {
        Genre genre1 = new Genre(1,"test genre 1");
        Author author2 = new Author(2, "test author 2");
        Book book2 = new Book(2, "test title book 2", author2, genre1, new ArrayList<>());

        Book resultBook = bookRepositoryJPA.findByTitle("test title book 2").orElseThrow();
        assertEquals(book2, resultBook);
    }

    @Test
    void givenNewBookWhenInsertThenReturnBookWithId() {
        Genre genre1 = new Genre(1,"test genre 1");
        Author author2 = new Author(2, "test author 2");
        Book newBook = new Book("new Test book title", author2, genre1);
        Book exptectedBook = new Book(7, "new Test book title", author2, genre1, new ArrayList<>());

        Book insertedBook = bookRepositoryJPA.save(newBook);
        assertEquals(exptectedBook, insertedBook);

        entityManager.detach(newBook);

        Book findedBook = bookRepositoryJPA.getById(7).orElseThrow();
        assertEquals(exptectedBook, findedBook);
    }

    @Test
    void givenNewBookWhenUpdateThenSaved() {
        Author author2 = new Author(2, "test author 2");
        Genre genre2 = new Genre(2,"test genre 2");
        Book newBook = new Book(1, "new book title", author2, genre2, new ArrayList<>());

        bookRepositoryJPA.save(newBook);

        entityManager.detach(newBook);

        Book resultedBook = bookRepositoryJPA.getById(1).orElseThrow();

        assertEquals(resultedBook, newBook);

    }

    @Test
    void givenIdWhenDeleteThenDeleted() {
        bookRepositoryJPA.deleteById(1);
        assertTrue(bookRepositoryJPA.getById(1).isEmpty());
    }
}