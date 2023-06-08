package ru.otus.hw06orm.persistance.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw06orm.persistance.entity.Author;
import ru.otus.hw06orm.persistance.entity.Book;
import ru.otus.hw06orm.persistance.entity.Comment;
import ru.otus.hw06orm.persistance.entity.Genre;
import ru.otus.hw06orm.persistance.repository.BookRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class BookRepositoryJpaTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void whenGetAllThenReturnCorrectCount(){
        List<Book> bookList = bookRepository.findAll();
        assertEquals(6, bookList.size());
    }

    @Test
    void whenFindAllThenReturnCorrectBookContent(){
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

        var book = bookRepository.findById(1L).orElseThrow();
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
        Genre genre1 = new Genre(1,"test genre 1");
        Author author2 = new Author(2, "test author 2");
        Book newBook = new Book("new Test book title", author2, genre1);

        Book insertedBook = bookRepository.save(newBook);

        entityManager.detach(insertedBook);

        Book findedBook = bookRepository.findById(7L).orElseThrow();
        var findedAuthor = findedBook.getAuthor();
        var findedGenre = findedBook.getGenre();

        assertEquals(7, findedBook.getId());
        assertEquals("new Test book title", findedBook.getTitle());
        assertEquals("test author 2", findedAuthor.getName());
        assertEquals("test genre 1", findedGenre.getName());
    }

    @Test
    void givenNewBookWithIdWhenSaveThenSaved() {
        var book = bookRepository.findById(1L).orElseThrow();
        var newAuthor = new Author(2, "test author 2");
        var newGenre = new Genre(2, "new Genre 2");
        book.setTitle("NewBook Title");
        book.setAuthor(newAuthor);
        book.setGenre(newGenre);
        bookRepository.save(book);
        entityManager.flush();
        entityManager.detach(book);

        Book resultBook = bookRepository.findById(1L).orElseThrow();
        Author resultAuthor = resultBook.getAuthor();
        Genre resultGenre = resultBook.getGenre();
        List<Comment> resultComments = resultBook.getComments();

        assertEquals("NewBook Title", resultBook.getTitle());
        assertEquals("test author 2", resultAuthor.getName());
        assertEquals("test genre 2", resultGenre.getName());
        assertEquals(2, resultComments.size());
    }

    @Test
    void givenNewCommentWhenSaveThenSavedSuccessfully(){
        var newComment = new Comment("New comment test");
        var book = bookRepository.findById(1L).orElseThrow();
        newComment.setBook(book);
        book.getComments().add(newComment);
        bookRepository.save(book);
        entityManager.detach(book);

        var updatedBook = bookRepository.findById(1L).orElseThrow();
        var comments = updatedBook.getComments();
        var lastComment = comments.get(comments.size() - 1);

        assertEquals(3, comments.size());
        assertEquals("New comment test", lastComment.getText());
    }

    @Test
    void givenRemovedCommentWhenSaveThenSavedSuccessfully(){
        var book = bookRepository.findById(1L).orElseThrow();
        var comments = book.getComments();
        assertEquals(2, comments.size());
        var comment = comments.get(0);
        comments.remove(comment);
        comment.setBook(null);

        bookRepository.save(book);
        entityManager.flush();
        entityManager.detach(book);

        var updatedBook = bookRepository.findById(1L).orElseThrow();
        var updatedComments = updatedBook.getComments();
        assertEquals(1, updatedComments.size());
    }

    @Test
    void givenIdWhenDeleteThenDeleted() {
        var book  = bookRepository.findById(1L).orElseThrow();
        bookRepository.delete(book);
        entityManager.flush();
        assertTrue(bookRepository.findById(1L).isEmpty());
    }
}