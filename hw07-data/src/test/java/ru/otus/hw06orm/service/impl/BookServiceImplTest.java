package ru.otus.hw06orm.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06orm.dto.BookDto;
import ru.otus.hw06orm.persistance.entity.Author;
import ru.otus.hw06orm.persistance.entity.Book;
import ru.otus.hw06orm.persistance.entity.Genre;
import ru.otus.hw06orm.exception.AuthorNotFoundException;
import ru.otus.hw06orm.exception.BookAlreadyExistsException;
import ru.otus.hw06orm.exception.BookNotFoundException;
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
        Book book1 = new Book(1, "Test book 1, author_1, genre_1", null, null, new ArrayList<>());
        Book book2 = new Book(2, "Test book 2, author_2, genre_1", null, null, new ArrayList<>());
        Book book3 = new Book(3, "Test book 3, author_1, genre_2", null, null, new ArrayList<>());
        Book book4 = new Book(4, "Test book 4, author_1, genre_1", null, null, new ArrayList<>());
        Book book5 = new Book(5, "Test book 5, author_2, genre_2", null, null, new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        Mockito.when(bookRepository.getAll()).thenReturn(books);

        List<BookDto> booksDtoTested = bookService.getAll();
        assertEquals(5, booksDtoTested.size());

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

        List<BookDto> booksTested = bookService.getAll();
        var bookDto = booksTested.get(0);
        assertEquals(1, bookDto.getId());
        assertEquals("Test book 1, author_1, genre_1", bookDto.getTitle());
        assertEquals("Test author 1", bookDto.getAuthorName());
        assertEquals("Test genre 1", bookDto.getGenreName());
    }

    @Test
    void givenAuthorWhenFindByAuthorThenReturnCorrectCount() {
        Author author1 = new Author(1, "Test author 1");

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
        Mockito.when(authorService.findByName(authorName)).thenReturn(author1);
        Mockito.when(bookRepository.findByAuthor(author1)).thenReturn(books);

        List<BookDto> resultBookDtos = bookService.findByAuthorName(authorName);
        assertEquals(3, resultBookDtos.size());
    }

    @Test
    void givenAuthorWhenFindByAuthorThenReturnCorrectBook() {
        Author author1 = new Author(1, "Test author 1");
        Genre genre1 = new Genre(1, "Test genre 1");
        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1, new ArrayList<>());
        Book book2 = new Book(2, "Test book 2, author_1, genre_1", author1, genre1, new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        String authorName = "Test author 1";
        Mockito.when(authorService.findByName(authorName)).thenReturn(author1);
        Mockito.when(bookRepository.findByAuthor(author1)).thenReturn(books);

        List<BookDto> resultBookDtos = bookService.findByAuthorName(authorName);
        var bookDto = resultBookDtos.get(1);
        assertEquals(2, bookDto.getId());
        assertEquals("Test book 2, author_1, genre_1", bookDto.getTitle());
        assertEquals("Test author 1", bookDto.getAuthorName());
        assertEquals("Test genre 1", bookDto.getGenreName());
    }

    @Test
    void givenEmptyAuthorWhenFindByAuthorThenAuthorNotFoundException(){
        String authorName = "Test author";
        Mockito.when(authorService.findByName(authorName)).thenThrow(AuthorNotFoundException.class);
        assertThrowsExactly(AuthorNotFoundException.class, () -> bookService.findByAuthorName(authorName));
    }

    @Test
    void givenGenreWhenFindByGenreThenReturnCorrectCount() {
        Author author1 = new Author(1, "Test author 1");

        Genre genre1 = new Genre(1, "Test genre 1");

        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1, new ArrayList<>());
        Book book3 = new Book(3, "Test book 3, author_1, genre_1", author1, genre1, new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book3);

        String genreName = "Test genre 1";
        Mockito.when(genreService.getByName(genreName)).thenReturn(genre1);
        Mockito.when(bookRepository.findByGenre(genre1)).thenReturn(books);

        List<BookDto> resultBookDtos = bookService.findByGenreName(genreName);
        assertEquals(2, resultBookDtos.size());
    }

    @Test
    void givenGenreWhenFindByGenreThenReturnCorrectBook() {
        Author author1 = new Author(1, "Test author 1");

        Genre genre1 = new Genre(1, "Test genre 1");

        Book book1 = new Book(1, "Test book 1, author_1, genre_1", author1, genre1, new ArrayList<>());
        Book book3 = new Book(3, "Test book 3, author_1, genre_1", author1, genre1, new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book3);

        String genreName = "Test genre 1";
        Mockito.when(genreService.getByName(genreName)).thenReturn(genre1);
        Mockito.when(bookRepository.findByGenre(genre1)).thenReturn(books);

        List<BookDto> resultBookDtos = bookService.findByGenreName(genreName);
        var bookDto = resultBookDtos.get(1);
        assertEquals(3, bookDto.getId());
        assertEquals("Test book 3, author_1, genre_1", bookDto.getTitle());
        assertEquals("Test author 1", bookDto.getAuthorName());
        assertEquals("Test genre 1", bookDto.getGenreName());
    }

    @Test
    void givenEmptyGenreWhenFindByGenreThenGenreNotFoundException(){
        String genreName = "Test genre";
        Mockito.when(genreService.getByName(genreName)).thenThrow(GenreNotFoundException.class);
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

        BookDto resultBookDto = bookService.get(id);
        assertEquals(1, resultBookDto.getId());
        assertEquals("Test author 1", resultBookDto.getAuthorName());
        assertEquals("Test genre 1", resultBookDto.getGenreName());
        assertEquals("Test book 1, author_1, genre_1", resultBookDto.getTitle());
    }

    @Test
    void givenWrongIdWhenGetThenReturnException(){
        long id = 1;
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());
        assertThrowsExactly(BookNotFoundException.class, () -> bookService.get(id));
    }

    @Test
    void givenTitleWhenFindByTitleThenReturnBook() {
        String title = "Test book 1, author_1, genre_1";
        Author author1 = new Author(1, "Test author 1");
        Genre genre1 = new Genre(1, "Test genre 1");
        Book book1 = new Book(1, title, author1, genre1, new ArrayList<>());
        var optionalBook1 = Optional.of(book1);

        Mockito.when(bookRepository.findByTitle(title)).thenReturn(optionalBook1);

        BookDto resultBookDto = bookService.findByTitle(title);
        assertEquals(1, resultBookDto.getId());
        assertEquals("Test author 1", resultBookDto.getAuthorName());
        assertEquals("Test genre 1", resultBookDto.getGenreName());
        assertEquals("Test book 1, author_1, genre_1", resultBookDto.getTitle());
    }

    @Test
    void givenFakeTitleWhenFindByTitleThenReturnBook() {
        String title = "Test book 1, author_1, genre_1";
        Mockito.when(bookRepository.findByTitle(title)).thenReturn(Optional.empty());
        assertThrowsExactly(BookNotFoundException.class, () -> bookService.findByTitle(title));
    }

    @Test
    void givenTitleAndAuthorAndGenreWhenAddThenBookWithId() {
        String titleName = "Test book 1, author_1, genre_1";
        String authorName = "Test author 1";
        String genreName = "Test genre 1";

        var author1 = new Author(1, authorName);
        var genre1 = new Genre(1, genreName);
        var book1 = new Book(titleName, author1, genre1);
        var withId = new Book(1, titleName, author1, genre1, new ArrayList<>());

        Mockito.when(bookRepository.findByTitle(titleName)).thenReturn(Optional.empty());
        Mockito.when(authorService.getOrCreate(authorName)).thenReturn(author1);
        Mockito.when(genreService.getOrCreate(genreName)).thenReturn(genre1);
        Mockito.when(bookRepository.save(book1)).thenReturn(withId);

        var createdBookDto = bookService.add(titleName, authorName, genreName);

        assertEquals(1, createdBookDto.getId());
        assertEquals("Test book 1, author_1, genre_1", createdBookDto.getTitle());
        assertEquals("Test author 1", createdBookDto.getAuthorName());
        assertEquals("Test genre 1", createdBookDto.getGenreName());
    }

    @Test
    void givenExistsTitleWhenAddThenException(){
        String titleName = "Test book 1, author_1, genre_1";
        var withId = new Book(1, titleName, null, null, new ArrayList<>());
        Mockito.when(bookRepository.findByTitle(titleName)).thenReturn(Optional.of(withId));
        assertThrowsExactly(BookAlreadyExistsException.class,
                () -> bookService.add(titleName, "Petia", "Test"));
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
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(oldBook));
        Mockito.when(authorService.getOrCreate(newAuthorName)).thenReturn(newAuthor);
        Mockito.when(genreService.getOrCreate(newGenreName)).thenReturn(newGenre);
        Mockito.when(bookRepository.save(newBook)).thenReturn(newBook);
        BookDto updatedBookDto = bookService.update(1,newTitleName, newAuthorName, newGenreName);
        assertEquals(1, updatedBookDto.getId());
        assertEquals("Test book 2, author_2, genre_2", updatedBookDto.getTitle());
        assertEquals("Test author 2", updatedBookDto.getAuthorName());
        assertEquals("Test genre 2", updatedBookDto.getGenreName());
    }


}