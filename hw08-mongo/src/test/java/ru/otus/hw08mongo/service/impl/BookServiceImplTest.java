package ru.otus.hw08mongo.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw08mongo.dto.BookDto;
import ru.otus.hw08mongo.exception.AuthorNotFoundException;
import ru.otus.hw08mongo.exception.BookAlreadyExistsException;
import ru.otus.hw08mongo.exception.BookNotFoundException;
import ru.otus.hw08mongo.exception.GenreNotFoundException;
import ru.otus.hw08mongo.persistance.entity.Author;
import ru.otus.hw08mongo.persistance.entity.Book;
import ru.otus.hw08mongo.persistance.entity.Genre;
import ru.otus.hw08mongo.persistance.repository.BookRepository;
import ru.otus.hw08mongo.service.AuthorService;
import ru.otus.hw08mongo.service.BookService;
import ru.otus.hw08mongo.service.GenreService;

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
        var books = getTestBooks();
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> booksDtoTested = bookService.getAll();
        assertEquals(5, booksDtoTested.size());
    }

    @Test
    void whenGetAllThenReturnCorrectBook() {
        var books =  getTestBooks();
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<BookDto> booksTested = bookService.getAll();
        var bookDto = booksTested.get(0);
        assertEquals("40b7315b-2f4a-4e80-b468-0de925ff743s", bookDto.getId());
        assertEquals("Test book 1, author_1, genre_1", bookDto.getTitle());
        assertEquals("Test author 1", bookDto.getAuthorName());
        assertEquals("Test genre 1", bookDto.getGenreName());
    }

    @Test
    void givenAuthorWhenFindByAuthorThenReturnCorrectCount() {
        var books = getTestBooks();
        var author1 = getTestAuthors().get(0);
        var authorBooks = books.stream().filter(book -> book.getAuthor().equals(author1)).toList();

        String authorName = "Test author 1";
        Mockito.when(authorService.findByName(authorName)).thenReturn(author1);
        Mockito.when(bookRepository.findBookByAuthor(author1)).thenReturn(authorBooks);

        List<BookDto> resultBookDtos = bookService.findByAuthorName(authorName);
        assertEquals(3, resultBookDtos.size());
    }

    @Test
    void givenAuthorWhenFindByAuthorThenReturnCorrectBook() {
        var books = getTestBooks();
        var author1 = getTestAuthors().get(0);
        var authorBooks = books.stream().filter(book -> book.getAuthor().equals(author1)).toList();

        String authorName = "Test author 1";
        Mockito.when(authorService.findByName(authorName)).thenReturn(author1);
        Mockito.when(bookRepository.findBookByAuthor(author1)).thenReturn(authorBooks);

        List<BookDto> resultBookDtos = bookService.findByAuthorName(authorName);
        var bookDto = resultBookDtos.get(1);
        assertEquals("40b7315b-2f4a-4e80-b468-0de925ff743e", bookDto.getId());
        assertEquals("Test book 3, author_1, genre_2", bookDto.getTitle());
        assertEquals("Test author 1", bookDto.getAuthorName());
        assertEquals("Test genre 2", bookDto.getGenreName());
    }

    @Test
    void givenEmptyAuthorWhenFindByAuthorThenAuthorNotFoundException() {
        String authorName = "Test author";
        Mockito.when(authorService.findByName(authorName)).thenThrow(AuthorNotFoundException.class);
        assertThrowsExactly(AuthorNotFoundException.class, () -> bookService.findByAuthorName(authorName));
    }

    @Test
    void givenGenreWhenFindByGenreThenReturnCorrectCount() {
        var books = getTestBooks();
        var genre1 = getTestGenres().get(0);
        var genreBooks = books.stream().filter(book -> book.getGenre().equals(genre1)).toList();

        String genreName = "Test genre 1";
        Mockito.when(genreService.getByName(genreName)).thenReturn(genre1);
        Mockito.when(bookRepository.findBookByGenre(genre1)).thenReturn(genreBooks);

        List<BookDto> resultBookDtos = bookService.findByGenreName(genreName);
        assertEquals(3, resultBookDtos.size());
    }

    @Test
    void givenGenreWhenFindByGenreThenReturnCorrectBook() {
        var books = getTestBooks();
        var genre1 = getTestGenres().get(0);
        var genreBooks = books.stream().filter(book -> book.getGenre().equals(genre1)).toList();

        String genreName = "Test genre 1";
        Mockito.when(genreService.getByName(genreName)).thenReturn(genre1);
        Mockito.when(bookRepository.findBookByGenre(genre1)).thenReturn(genreBooks);

        List<BookDto> resultBookDtos = bookService.findByGenreName(genreName);
        var bookDto = resultBookDtos.get(1);
        assertEquals("40b7315b-2f4a-4e80-b468-0de925ff743d", bookDto.getId());
        assertEquals("Test book 2, author_2, genre_1", bookDto.getTitle());
        assertEquals("Test author 2", bookDto.getAuthorName());
        assertEquals("Test genre 1", bookDto.getGenreName());
    }

    @Test
    void givenEmptyGenreWhenFindByGenreThenGenreNotFoundException() {
        String genreName = "Test genre";
        Mockito.when(genreService.getByName(genreName)).thenThrow(GenreNotFoundException.class);
        assertThrowsExactly(GenreNotFoundException.class, () -> bookService.findByGenreName(genreName));
    }

    @Test
    void givenIdWhenGetThenReturnBook() {
        String id = "40b7315b-2f4a-4e80-b468-0de925ff743s";
        var book1 = getTestBooks().get(0);
        var optionalBook1 = Optional.of(book1);

        Mockito.when(bookRepository.findById(id)).thenReturn(optionalBook1);

        BookDto resultBookDto = bookService.get(id);
        assertEquals("40b7315b-2f4a-4e80-b468-0de925ff743s", resultBookDto.getId());
        assertEquals("Test author 1", resultBookDto.getAuthorName());
        assertEquals("Test genre 1", resultBookDto.getGenreName());
        assertEquals("Test book 1, author_1, genre_1", resultBookDto.getTitle());
    }

    @Test
    void givenWrongIdWhenGetThenReturnException() {
        String id = "test";
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());
        assertThrowsExactly(BookNotFoundException.class, () -> bookService.get(id));
    }

    @Test
    void givenTitleWhenFindByTitleThenReturnBook() {
        String title = "Test book 1, author_1, genre_1";
        Book book1 = getTestBooks().get(0);
        var optionalBook1 = Optional.of(book1);

        Mockito.when(bookRepository.findBookByTitle(title)).thenReturn(optionalBook1);

        BookDto resultBookDto = bookService.findByTitle(title);
        assertEquals("40b7315b-2f4a-4e80-b468-0de925ff743s", resultBookDto.getId());
        assertEquals("Test author 1", resultBookDto.getAuthorName());
        assertEquals("Test genre 1", resultBookDto.getGenreName());
        assertEquals("Test book 1, author_1, genre_1", resultBookDto.getTitle());
    }

    @Test
    void givenFakeTitleWhenFindByTitleThenReturnBook() {
        String title = "Test book 1, author_1, genre_1";
        Mockito.when(bookRepository.findBookByTitle(title)).thenReturn(Optional.empty());
        assertThrowsExactly(BookNotFoundException.class, () -> bookService.findByTitle(title));
    }

    @Test
    void givenTitleAndAuthorAndGenreWhenAddThenBookWithId() {
        String titleName = "Test book 1, author_1, genre_1";
        String authorName = "Test author 1";
        String genreName = "Test genre 1";

        var author1 = new Author("40b7315b-2f4a-4e80-b468-0de925ff743d", authorName);
        var genre1 = new Genre("40b7315b-2f4a-4e80-b468-0de925ff743d", genreName);
        var book1 = new Book(titleName, author1, genre1);
        var withId = new Book("40b7315b-2f4a-4e80-b468-0de925ff743s", titleName, author1, genre1, new ArrayList<>());

        Mockito.when(bookRepository.findBookByTitle(titleName)).thenReturn(Optional.empty());
        Mockito.when(authorService.getOrCreate(authorName)).thenReturn(author1);
        Mockito.when(genreService.getOrCreate(genreName)).thenReturn(genre1);
        Mockito.when(bookRepository.save(book1)).thenReturn(withId);

        var createdBookDto = bookService.add(titleName, authorName, genreName);

        assertEquals("40b7315b-2f4a-4e80-b468-0de925ff743s", createdBookDto.getId());
        assertEquals("Test book 1, author_1, genre_1", createdBookDto.getTitle());
        assertEquals("Test author 1", createdBookDto.getAuthorName());
        assertEquals("Test genre 1", createdBookDto.getGenreName());
    }

    @Test
    void givenExistsTitleWhenAddThenException() {
        String titleName = "Test book 1, author_1, genre_1";
        var withId = new Book("40b7315b-2f4a-4e80-b468-0de925ff743s", titleName, null, null, new ArrayList<>());
        Mockito.when(bookRepository.findBookByTitle(titleName)).thenReturn(Optional.of(withId));
        assertThrowsExactly(BookAlreadyExistsException.class,
                () -> bookService.add(titleName, "Petia", "Test"));
    }

    @Test
    void givenTitleAndAuthorAndGenreWhenUpdateThenBookWithNewTitleAndAuthorAndGenre() {
        var newTitleName = "Test book 2, author_2, genre_2";
        var newAuthorName = "Test author 2";
        var newGenreName = "Test genre 2";
        Book oldBook = getTestBooks().get(0);
        Author newAuthor = getTestAuthors().get(1);
        Genre newGenre = getTestGenres().get(1);
        Book newBook = new Book(oldBook.getId(),
                newTitleName, newAuthor, newGenre, new ArrayList<>());
        Mockito.when(bookRepository.findById("test")).thenReturn(Optional.of(oldBook));
        Mockito.when(authorService.getOrCreate(newAuthorName)).thenReturn(newAuthor);
        Mockito.when(genreService.getOrCreate(newGenreName)).thenReturn(newGenre);
        Mockito.when(bookRepository.save(newBook)).thenReturn(newBook);
        BookDto updatedBookDto = bookService.update("test",newTitleName, newAuthorName, newGenreName);
        assertEquals("40b7315b-2f4a-4e80-b468-0de925ff743s", updatedBookDto.getId());
        assertEquals("Test book 2, author_2, genre_2", updatedBookDto.getTitle());
        assertEquals("Test author 2", updatedBookDto.getAuthorName());
        assertEquals("Test genre 2", updatedBookDto.getGenreName());
    }

    private List<Book> getTestBooks() {
        var genres = getTestGenres();
        var genre1 = genres.get(0);
        var genre2 = genres.get(1);
        var authors = getTestAuthors();
        var author1 = authors.get(0);
        var author2 = authors.get(1);
        Book book1 = new Book(
                "40b7315b-2f4a-4e80-b468-0de925ff743s", "Test book 1, author_1, genre_1",
                author1, genre1, new ArrayList<>());
        Book book2 = new Book("40b7315b-2f4a-4e80-b468-0de925ff743d", "Test book 2, author_2, genre_1",
                author2, genre1, new ArrayList<>());
        Book book3 = new Book("40b7315b-2f4a-4e80-b468-0de925ff743e", "Test book 3, author_1, genre_2",
                author1, genre2, new ArrayList<>());
        Book book4 = new Book("40b7315b-2f4a-4e80-b468-0de925ff743f", "Test book 4, author_1, genre_1",
                author1, genre1, new ArrayList<>());
        Book book5 = new Book("40b7315b-2f4a-4e80-b468-0de925ff743g", "Test book 5, author_2, genre_2",
                author2, genre2, new ArrayList<>());
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        return books;
    }

    private List<Genre> getTestGenres() {
        Genre genre1 = new Genre("40b7315b-2f4a-4e80-b468-0de925ff743d", "Test genre 1");
        Genre genre2 = new Genre("40b7315b-2f4a-4e80-b468-0de925ff743e", "Test genre 2");
        List<Genre> genres = new ArrayList<>();
        genres.add(genre1);
        genres.add(genre2);
        return genres;
    }

    private List<Author> getTestAuthors() {
        Author author1 = new Author("40b7315b-2f4a-4e80-b468-0de925ff743d", "Test author 1");
        Author author2 = new Author("40b7315b-2f4a-4e80-b468-0de925ff743e", "Test author 2");
        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);
        return authors;

    }
}