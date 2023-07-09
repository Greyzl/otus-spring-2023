package ru.otus.hw08mongo.persistance.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw08mongo.persistance.entity.Author;
import ru.otus.hw08mongo.persistance.entity.Book;
import ru.otus.hw08mongo.persistance.entity.Comment;
import ru.otus.hw08mongo.persistance.entity.Genre;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookRepositoryJpaTest extends AuthorRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void whenGetAllThenReturnCorrectCount() {
        List<Book> bookList = bookRepository.findAll();
        assertEquals(5, bookList.size());
    }

    @Test
    void whenFindAllThenReturnCorrectBookContent() {
        List<Book> bookList = bookRepository.findAll();
        var book = bookList.get(1);
        var author = book.getAuthor();
        var genre = book.getGenre();
        var comments = book.getComments();

        assertEquals("test title book 2", book.getTitle());
        assertEquals("test author 2", author.getName());
        assertEquals("test genre 1", genre.getName());

        assertEquals(1, comments.size());
        var comment = comments.get(0);

        assertEquals("THIRD COMMENT", comment.getText());
    }

    @Test
    void givenIdWhenFindByIdThenReturnBook() {
        var books = bookRepository.findAll();
        var bookId = books.get(0).getId();
        var book = bookRepository.findById(bookId).orElseThrow();
        var author = book.getAuthor();
        var genre = book.getGenre();
        var comments = book.getComments();

        assertEquals("test title book 1", book.getTitle());
        assertEquals("test author 1", author.getName());
        assertEquals("test genre 1", genre.getName());

        assertEquals(2, comments.size());
        var comment = comments.get(1);

        assertEquals("SECOND COMMENT", comment.getText());
    }

    @Test
    void givenTitleWhenFindBookByTitleThenReturnBook() {

        var book = bookRepository.findBookByTitle("test title book 2").orElseThrow();
        var author = book.getAuthor();
        var genre = book.getGenre();
        var comments = book.getComments();


        assertEquals("test title book 2", book.getTitle());
        assertEquals("test author 2", author.getName());
        assertEquals("test genre 1", genre.getName());

        assertEquals(1, comments.size());
        var comment = comments.get(0);

        assertEquals("THIRD COMMENT", comment.getText());
    }

    @Test
    void givenNewBookWithoutIdWhenSaveThenReturnBookWithId() {
        Genre genre1 = genreRepository.save(new Genre("test genre 1"));
        Author author2 = authorRepository.save(new Author("test author 2"));
        Book newBook = new Book("new Test book title", author2, genre1);

        Book insertedBook = bookRepository.save(newBook);


        Book findedBook = bookRepository.findById(insertedBook.getId()).orElseThrow();
        var findedAuthor = findedBook.getAuthor();
        var findedGenre = findedBook.getGenre();

        assertNotNull(findedBook.getId());
        assertEquals("new Test book title", findedBook.getTitle());
        assertEquals("test author 2", findedAuthor.getName());
        assertEquals("test genre 1", findedGenre.getName());
    }

    @Test
    void givenNewBookWithIdWhenSaveThenSaved() {
        var books = bookRepository.findAll();
        var book = bookRepository.findById(books.get(0).getId()).orElseThrow();
        var newAuthor = authorRepository.save(new Author("test author 2"));
        var newGenre = genreRepository.save(new Genre("test genre 2"));
        book.setTitle("NewBook Title");
        book.setAuthor(newAuthor);
        book.setGenre(newGenre);
        bookRepository.save(book);

        Book resultBook = bookRepository.findById(book.getId()).orElseThrow();
        Author resultAuthor = resultBook.getAuthor();
        Genre resultGenre = resultBook.getGenre();
        List<Comment> resultComments = resultBook.getComments();

        assertEquals("NewBook Title", resultBook.getTitle());
        assertEquals("test author 2", resultAuthor.getName());
        assertEquals("test genre 2", resultGenre.getName());
        assertEquals(2, resultComments.size());
    }

    @Test
    void givenNewCommentWhenSaveThenSavedSuccessfully() {
        var newComment = new Comment("New comment test");
        var books = bookRepository.findAll();
        var book = bookRepository.findById(books.get(0).getId()).orElseThrow();

        bookRepository.addComment(book, newComment);

        var updatedBook = bookRepository.findById(book.getId()).orElseThrow();
        var comments = updatedBook.getComments();
        var lastComment = comments.get(comments.size() - 1);

        assertEquals(3, comments.size());
        assertEquals("New comment test", lastComment.getText());
    }

    @Test
    void givenRemovedCommentWhenSaveThenSavedSuccessfully() {
        var books = bookRepository.findAll();
        var book = bookRepository.findById(books.get(0).getId()).orElseThrow();
        var comments = book.getComments();
        assertEquals(2, comments.size());
        var comment = comments.get(0);

        bookRepository.deleteComment(book, comment);

        var updatedBook = bookRepository.findById(book.getId()).orElseThrow();
        var updatedComments = updatedBook.getComments();
        assertEquals(1, updatedComments.size());
        assertThrows(NoSuchElementException.class, () -> commentRepository.findById(comment.getId()).orElseThrow());
    }

    @Test
    void givenIdWhenDeleteThenDeleted() {
        var books = bookRepository.findAll();
        var book  = bookRepository.findById(books.get(0).getId()).orElseThrow();

        var comments = book.getComments();
        bookRepository.deleteWithComments(book);
        assertTrue(bookRepository.findById(book.getId()).isEmpty());
        assertThrows(NoSuchElementException.class,
                () -> commentRepository.findById(comments.get(0).getId()).orElseThrow());
    }
}