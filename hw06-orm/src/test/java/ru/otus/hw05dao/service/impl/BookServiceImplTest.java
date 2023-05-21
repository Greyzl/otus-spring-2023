package ru.otus.hw05dao.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw05dao.persistance.dao.BookDao;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.entity.Genre;
import ru.otus.hw05dao.exception.AuthorNotFoundException;
import ru.otus.hw05dao.exception.GenreNotFoundException;
import ru.otus.hw05dao.service.AuthorService;
import ru.otus.hw05dao.service.BookService;
import ru.otus.hw05dao.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceImplTest {

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookDao bookDao;


    @Autowired
    private BookService bookService;


    @Test
    void whenGetAll_thenReturnBooks() {
        Author author1 = new Author(1, "Test author 1");
        Author author2 = new Author(2, "Test author 2");
        Genre genre1 = new Genre(1, "Test genre 1");
        Genre genre2 = new Genre(2, "Test genre 2");
        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1);
        Book book2 = new Book(2, "Test book 2, author_2, genre_1", author2, genre1);
        Book book3 = new Book(3, "Test book 3, author_1, genre_2", author1, genre2);
        Book book4 = new Book(3, "Test book 4, author_1, genre_1", author1, genre2);
        Book book5 = new Book(3, "Test book 5, author_2, genre_2", author1, genre2);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        Mockito.when(bookDao.getAll()).thenReturn(books);

        Author expectedAuthor1 = author1.toBuilder().build();
        Author expectedAuthor2 = author2.toBuilder().build();
        Genre expectedGenre1 = genre1.toBuilder().build();
        Genre expectedGenre2 = genre2.toBuilder().build();
        Book expectedBook1 = book1.toBuilder().setAuthor(expectedAuthor1).setGenre(expectedGenre1).build();
        Book expectedBook2 = book2.toBuilder().setAuthor(expectedAuthor2).setGenre(expectedGenre1).build();
        Book expectedBook3 = book3.toBuilder().setAuthor(expectedAuthor1).setGenre(expectedGenre2).build();
        Book expectedBook4 = book4.toBuilder().setAuthor(expectedAuthor1).setGenre(expectedGenre1).build();
        Book expectedBook5 = book5.toBuilder().setAuthor(expectedAuthor2).setGenre(expectedGenre2).build();

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(expectedBook1);
        expectedBooks.add(expectedBook2);
        expectedBooks.add(expectedBook3);
        expectedBooks.add(expectedBook4);
        expectedBooks.add(expectedBook5);


        List<Book> books_tested = bookService.getAll();
        assertIterableEquals(expectedBooks, books_tested);
    }

    @Test
    void givenAuthor_whenFindByAuthor_thenReturnBooksByAuthor() {
        Author author1 = new Author(1, "Test author 1");
        var mayBeAuthor = Optional.of(author1);
        Genre genre1 = new Genre(1, "Test genre 1");
        Genre genre2 = new Genre(2, "Test genre 2");
        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1);
        Book book2 = new Book(2, "Test book 2, author_1, genre_2", author1, genre2);
        Book book3 = new Book(3, "Test book 3, author_1, genre_1", author1, genre2);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);

        Author expectedAuthor1 = author1.toBuilder().build();
        Genre expectedGenre1 = genre1.toBuilder().build();
        Genre expectedGenre2 = genre2.toBuilder().build();
        Book expectedBook1 = book1.toBuilder().setAuthor(expectedAuthor1).setGenre(expectedGenre1).build();
        Book expectedBook2 = book2.toBuilder().setAuthor(expectedAuthor1).setGenre(expectedGenre2).build();
        Book expectedBook3 = book3.toBuilder().setAuthor(expectedAuthor1).setGenre(expectedGenre1).build();

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(expectedBook1);
        expectedBooks.add(expectedBook2);
        expectedBooks.add(expectedBook3);

        String authorName = "Test author";
        Mockito.when(authorService.findByName(authorName)).thenReturn(mayBeAuthor);
        Mockito.when(bookDao.findByAuthor(author1)).thenReturn(books);

        List<Book> resultBooks = bookService.findByAuthorName(authorName);
        assertIterableEquals(expectedBooks, resultBooks);
    }

    @Test
    void givenEmptyAuthor_whenFindByAuthor_thenAuthorNotFoundException(){
        String authorName = "Test author";
        Optional<Author> emptyAuthor = Optional.empty();
        Mockito.when(authorService.findByName(authorName)).thenReturn(emptyAuthor);
        assertThrowsExactly(AuthorNotFoundException.class, () -> bookService.findByAuthorName(authorName));
    }

    @Test
    void givenGenre_whenFindByGenre_thenReturnBooksByGenre() {
        Author author1 = new Author(1, "Test author 1");
        Author author2 = new Author(1, "Test author 2");

        Genre genre1 = new Genre(1, "Test genre 1");
        var mayBeGenre = Optional.of(genre1);

        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1);
        Book book2 = new Book(2, "Test book 2, author_1, genre_2", author2, genre1);
        Book book3 = new Book(3, "Test book 3, author_1, genre_1", author1, genre1);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);

        Author expectedAuthor1 = author1.toBuilder().build();
        Author expectedAuthor2 = author2.toBuilder().build();
        Genre expectedGenre1 = genre1.toBuilder().build();
        Book expectedBook1 = book1.toBuilder().setAuthor(expectedAuthor1).setGenre(expectedGenre1).build();
        Book expectedBook2 = book2.toBuilder().setAuthor(expectedAuthor2).setGenre(expectedGenre1).build();
        Book expectedBook3 = book3.toBuilder().setAuthor(expectedAuthor1).setGenre(expectedGenre1).build();

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(expectedBook1);
        expectedBooks.add(expectedBook2);
        expectedBooks.add(expectedBook3);

        String genreName = "Test genre";
        Mockito.when(genreService.getByName(genreName)).thenReturn(mayBeGenre);
        Mockito.when(bookDao.findByGenre(genre1)).thenReturn(books);

        List<Book> resultBooks = bookService.findByGenreName(genreName);
        assertIterableEquals(expectedBooks, resultBooks);
    }

    @Test
    void givenEmptyGenre_whenFindByGenre_thenGenreNotFoundException(){
        String genreName = "Test genre";
        Optional<Genre> emptyGenre = Optional.empty();
        Mockito.when(genreService.getByName(genreName)).thenReturn(emptyGenre);
        assertThrowsExactly(GenreNotFoundException.class, () -> bookService.findByGenreName(genreName));
    }

    @Test
    void givenId_whenGet_thenReturnBook(){
        long id = 1;
        Author author1 = new Author(1, "Test author 1");
        Genre genre1 = new Genre(1, "Test genre 1");
        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1);
        var optionalBook1 = Optional.of(book1);

        Author expectedAuthor = author1.toBuilder().build();
        Genre expectedGenre = genre1.toBuilder().build();
        Book expectedBook = book1.toBuilder().setAuthor(expectedAuthor).setGenre(expectedGenre).build();

        Mockito.when(bookDao.getById(id)).thenReturn(optionalBook1);

        Book resultBook = bookService.get(id).orElseThrow();
        assertEquals(expectedBook, resultBook);
    }

    @Test
    void givenFakeId_whenGet_thenEmptyOptional(){
        long id = 0;
        Optional<Book> emptyBook = Optional.empty();
        Mockito.when(bookDao.getById(id)).thenReturn(emptyBook);
        assertTrue(bookService.get(id).isEmpty());
    }

    @Test
    void givenTitle_whenFindByTitle_thenReturnBook() {
        String title = "Test book 1, author_1, genre_1";
        Author author1 = new Author(1, "Test author 1");
        Genre genre1 = new Genre(1, "Test genre 1");
        Book book1 = new Book(1, title, author1, genre1);
        var optionalBook1 = Optional.of(book1);

        Author expectedAuthor = author1.toBuilder().build();
        Genre expectedGenre = genre1.toBuilder().build();
        Book expectedBook = book1.toBuilder().setAuthor(expectedAuthor).setGenre(expectedGenre).build();

        Mockito.when(bookDao.findByTitle(title)).thenReturn(optionalBook1);

        Book resultBook = bookService.findByTitle(title).orElseThrow();
        assertEquals(expectedBook, resultBook);
    }

    @Test
    void givenFakeTitle_whenFindByTitle_thenReturnBook() {
        String title = "Test book 1, author_1, genre_1";
        Optional<Book> emptyBook = Optional.empty();
        Mockito.when(bookDao.findByTitle(title)).thenReturn(emptyBook);
        assertTrue(bookService.findByTitle(title).isEmpty());
    }

    @Test
    void givenTitleAndAuthorAndGenre_whenAdd_thenBookWithId() {
        String titleName = "Test book 1, author_1, genre_1";
        String authorName = "Test author 1";
        String genreName = "Test genre 1";

        Author author1 = new Author(1, authorName);
        Genre genre1 = new Genre(1, genreName);
        Book book1 = new Book(titleName, author1, genre1);
        Book withId = new Book(1, titleName, author1, genre1);

        Mockito.when(authorService.getOrCreate(authorName)).thenReturn(author1);
        Mockito.when(genreService.getOrCreate(genreName)).thenReturn(genre1);
        Mockito.when(bookDao.insert(book1)).thenReturn(withId);

        Book createdBook = bookService.add(titleName, authorName, genreName);

        assertEquals(withId, createdBook);
    }

    @Test
    void givenTitleAndAuthorAndGenre_whenUpdate_thenBookWithNewTitleAndAuthorAndGenre() {
        String oldTitleName = "Test book 1, author_1, genre_1";
        String oldAuthorName = "Test author 1";
        String oldGenreName = "Test genre 1";

        String newTitleName = "Test book 2, author_2, genre_2";
        String newAuthorName = "Test author 2";
        String newGenreName = "Test genre 2";

        Author oldAuthor = new Author(1, oldAuthorName);
        Genre oldGenre = new Genre(1, oldGenreName);
        Book oldBook = new Book(1, oldTitleName, oldAuthor, oldGenre);

        Author newAuthor = new Author(2, newAuthorName);
        Genre newGenre = new Genre(2, newGenreName);
        Book newBook = new Book(1, newTitleName, newAuthor, newGenre);

        Mockito.when(authorService.getOrCreate(newAuthorName)).thenReturn(newAuthor);
        Mockito.when(genreService.getOrCreate(newGenreName)).thenReturn(newGenre);

        Book updatedBook = bookService.update(oldBook,newTitleName, newAuthorName, newGenreName);

        assertEquals(newBook, updatedBook);
    }

}