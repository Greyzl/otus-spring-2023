package ru.otus.hw06orm.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06orm.entity.Author;
import ru.otus.hw06orm.entity.Book;
import ru.otus.hw06orm.entity.Genre;
import ru.otus.hw06orm.exception.AuthorNotFoundException;
import ru.otus.hw06orm.exception.GenreNotFoundException;
import ru.otus.hw06orm.persistance.repository.BookRepository;
import ru.otus.hw06orm.service.AuthorService;
import ru.otus.hw06orm.service.BookService;
import ru.otus.hw06orm.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BookServiceImplTest {

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookRepository bookRepository;


    @Autowired
    private BookService bookService;

    @Test
    void whenGetAllThenReturnCorrectCount() {
        Author author1 = new Author(1, "Test author 1");
        Author author2 = new Author(2, "Test author 2");
        Genre genre1 = new Genre(1, "Test genre 1");
        Genre genre2 = new Genre(2, "Test genre 2");
        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1, new ArrayList<>());
        Book book2 = new Book(2, "Test book 2, author_2, genre_1", author2, genre1, new ArrayList<>());
        Book book3 = new Book(3, "Test book 3, author_1, genre_2", author1, genre2, new ArrayList<>());
        Book book4 = new Book(4, "Test book 4, author_1, genre_1", author1, genre2, new ArrayList<>());
        Book book5 = new Book(5, "Test book 5, author_2, genre_2", author1, genre2, new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        Mockito.when(bookRepository.getAll()).thenReturn(books);

        List<Book> booksTested = bookService.getAll();
        assertEquals(5, booksTested.size());

    }

    @Test
    void whenGetAllThenReturnCorrectBook() {
        Author author1 = new Author(1, "Test author 1");
        Author author2 = new Author(2, "Test author 2");
        Genre genre1 = new Genre(1, "Test genre 1");
        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1, new ArrayList<>());
        Book book2 = new Book(2, "Test book 2, author_2, genre_1", author2, genre1, new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        Mockito.when(bookRepository.getAll()).thenReturn(books);

        List<Book> booksTested = bookService.getAll();
        var book = booksTested.get(0);
        assertEquals(1, book.getId());
        assertEquals("Test book 1, author_1, genre_1", book.getTitle());
        assertEquals("Test author 1", book.getAuthor().getName());
        assertEquals("Test genre 1", book.getGenre().getName());
    }

    @Test
    void givenAuthorWhenFindByAuthorThenReturnCorrectCount() {
        Author author1 = new Author(1, "Test author 1");

        var mayBeAuthor = Optional.of(author1);

        Genre genre1 = new Genre(1, "Test genre 1");
        Genre genre2 = new Genre(2, "Test genre 2");
        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1, new ArrayList<>());
        Book book2 = new Book(2, "Test book 2, author_1, genre_2", author1, genre2, new ArrayList<>());
        Book book3 = new Book(3, "Test book 3, author_1, genre_1", author1, genre2, new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);

        String authorName = "Test author 1";
        Mockito.when(authorService.findByName(authorName)).thenReturn(mayBeAuthor);
        Mockito.when(bookRepository.findByAuthor(author1)).thenReturn(books);

        List<Book> resultBooks = bookService.findByAuthorName(authorName);
        assertEquals(3, resultBooks.size());
    }

    @Test
    void givenAuthorWhenFindByAuthorThenReturnCorrectBook() {
        Author author1 = new Author(1, "Test author 1");
        Genre genre1 = new Genre(1, "Test genre 1");
        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1, new ArrayList<>());
        Book book2 = new Book(2, "Test book 2, author_1, genre_1", author1, genre1, new ArrayList<>());

        var mayBeAuthor = Optional.of(author1);
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        String authorName = "Test author 1";
        Mockito.when(authorService.findByName(authorName)).thenReturn(mayBeAuthor);
        Mockito.when(bookRepository.findByAuthor(author1)).thenReturn(books);

        List<Book> resultBooks = bookService.findByAuthorName(authorName);
        var book = resultBooks.get(1);
        assertEquals(2, book.getId());
        assertEquals("Test book 2, author_1, genre_1", book.getTitle());
        assertEquals("Test author 1", book.getAuthor().getName());
        assertEquals("Test genre 1", book.getGenre().getName());
    }

    @Test
    void givenEmptyAuthorWhenFindByAuthorThenAuthorNotFoundException(){
        String authorName = "Test author";
        Optional<Author> emptyAuthor = Optional.empty();
        Mockito.when(authorService.findByName(authorName)).thenReturn(emptyAuthor);
        assertThrowsExactly(AuthorNotFoundException.class, () -> bookService.findByAuthorName(authorName));
    }

    @Test
    void givenGenreWhenFindByGenreThenReturnCorrectCount() {
        Author author1 = new Author(1, "Test author 1");

        Genre genre1 = new Genre(1, "Test genre 1");
        var mayBeGenre = Optional.of(genre1);

        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1, new ArrayList<>());
        Book book3 = new Book(3, "Test book 3, author_1, genre_1", author1, genre1, new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book3);

        String genreName = "Test genre 1";
        Mockito.when(genreService.getByName(genreName)).thenReturn(mayBeGenre);
        Mockito.when(bookRepository.findByGenre(genre1)).thenReturn(books);

        List<Book> resultBooks = bookService.findByGenreName(genreName);
        assertEquals(2, resultBooks.size());
    }

    @Test
    void givenGenreWhenFindByGenreThenReturnCorrectBook() {
        Author author1 = new Author(1, "Test author 1");

        Genre genre1 = new Genre(1, "Test genre 1");
        var mayBeGenre = Optional.of(genre1);

        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1, new ArrayList<>());
        Book book3 = new Book(3, "Test book 3, author_1, genre_1", author1, genre1, new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book3);

        String genreName = "Test genre 1";
        Mockito.when(genreService.getByName(genreName)).thenReturn(mayBeGenre);
        Mockito.when(bookRepository.findByGenre(genre1)).thenReturn(books);

        List<Book> resultBooks = bookService.findByGenreName(genreName);
        var book = resultBooks.get(1);
        assertEquals(3, book.getId());
        assertEquals("Test book 3, author_1, genre_1", book.getTitle());
        assertEquals("Test author 1", book.getAuthor().getName());
        assertEquals("Test genre 1", book.getGenre().getName());
    }

    @Test
    void givenEmptyGenreWhenFindByGenreThenGenreNotFoundException(){
        String genreName = "Test genre";
        Optional<Genre> emptyGenre = Optional.empty();
        Mockito.when(genreService.getByName(genreName)).thenReturn(emptyGenre);
        assertThrowsExactly(GenreNotFoundException.class, () -> bookService.findByGenreName(genreName));
    }

    @Test
    void givenIdWhenGetThenReturnBook(){
        long id = 1;
        Author author1 = new Author(1, "Test author 1");
        Genre genre1 = new Genre(1, "Test genre 1");
        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1, new ArrayList<>());
        var optionalBook1 = Optional.of(book1);

        Mockito.when(bookRepository.findById(id)).thenReturn(optionalBook1);

        Book resultBook = bookService.get(id).orElseThrow();
        assertEquals(1, resultBook.getId());
        assertEquals("Test author 1", resultBook.getAuthor().getName());
        assertEquals("Test genre 1", resultBook.getGenre().getName());
        assertEquals("Test book 1, author_1, genre_1", resultBook.getTitle());
    }

    @Test
    void givenFakeIdWhenGetThenEmptyOptional(){
        long id = 0;
        Optional<Book> emptyBook = Optional.empty();
        Mockito.when(bookRepository.findById(id)).thenReturn(emptyBook);
        assertTrue(bookService.get(id).isEmpty());
    }

    @Test
    void givenTitleWhenFindByTitleThenReturnBook() {
        String title = "Test book 1, author_1, genre_1";
        Author author1 = new Author(1, "Test author 1");
        Genre genre1 = new Genre(1, "Test genre 1");
        Book book1 = new Book(1, title, author1, genre1, new ArrayList<>());
        var optionalBook1 = Optional.of(book1);

        Mockito.when(bookRepository.findByTitle(title)).thenReturn(optionalBook1);

        Book resultBook = bookService.findByTitle(title).orElseThrow();
        assertEquals(1, resultBook.getId());
        assertEquals("Test author 1", resultBook.getAuthor().getName());
        assertEquals("Test genre 1", resultBook.getGenre().getName());
        assertEquals("Test book 1, author_1, genre_1", resultBook.getTitle());
    }

    @Test
    void givenFakeTitleWhenFindByTitleThenReturnBook() {
        String title = "Test book 1, author_1, genre_1";
        Optional<Book> emptyBook = Optional.empty();
        Mockito.when(bookRepository.findByTitle(title)).thenReturn(emptyBook);
        assertTrue(bookService.findByTitle(title).isEmpty());
    }

    @Test
    void givenTitleAndAuthorAndGenreWhenAddThenBookWithId() {
        String titleName = "Test book 1, author_1, genre_1";
        String authorName = "Test author 1";
        String genreName = "Test genre 1";

        Author author1 = new Author(1, authorName);
        Genre genre1 = new Genre(1, genreName);
        Book book1 = new Book(titleName, author1, genre1);
        Book withId = new Book(1, titleName, author1, genre1, new ArrayList<>());

        Mockito.when(authorService.getOrCreate(authorName)).thenReturn(author1);
        Mockito.when(genreService.getOrCreate(genreName)).thenReturn(genre1);
        Mockito.when(bookRepository.save(book1)).thenReturn(withId);

        Book createdBook = bookService.add(titleName, authorName, genreName);

        assertEquals(withId, createdBook);
    }

    @Test
    void givenTitleAndAuthorAndGenreWhenUpdateThenBookWithNewTitleAndAuthorAndGenre() {
        String oldTitleName = "Test book 1, author_1, genre_1";
        String oldAuthorName = "Test author 1";
        String oldGenreName = "Test genre 1";

        String newTitleName = "Test book 2, author_2, genre_2";
        String newAuthorName = "Test author 2";
        String newGenreName = "Test genre 2";

        Author oldAuthor = new Author(1, oldAuthorName);
        Genre oldGenre = new Genre(1, oldGenreName);
        Book oldBook = new Book(1, oldTitleName, oldAuthor, oldGenre, new ArrayList<>());

        Author newAuthor = new Author(2, newAuthorName);
        Genre newGenre = new Genre(2, newGenreName);
        Book newBook = new Book(1, newTitleName, newAuthor, newGenre, new ArrayList<>());

        Mockito.when(authorService.getOrCreate(newAuthorName)).thenReturn(newAuthor);
        Mockito.when(genreService.getOrCreate(newGenreName)).thenReturn(newGenre);
        Mockito.when(bookRepository.save(newBook)).thenReturn(newBook);

        Book updatedBook = bookService.update(oldBook,newTitleName, newAuthorName, newGenreName);

        assertEquals(newBook, updatedBook);
    }

}