package ru.otus.hw06orm.persistance.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw06orm.entity.Author;
import ru.otus.hw06orm.entity.Book;
import ru.otus.hw06orm.entity.Comment;
import ru.otus.hw06orm.entity.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(BookRepositoryJPA.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJPA bookRepositoryJPA;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void whenGetAllThenReturnCorrectCount(){
        List<Book> bookList = bookRepositoryJPA.getAll();
        assertEquals(6, bookList.size());
    }

    @Test
    void whenGetAllThenReturnCorrectBookContent(){
        List<Book> bookList = bookRepositoryJPA.getAll();
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
    void givenIdWhenGetByIdThenReturnBook() {

        var book = bookRepositoryJPA.findById(1).orElseThrow();
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
    void givenTitleWhenFindByTitleThenReturnBook() {

        var book = bookRepositoryJPA.findByTitle("test title book 2").orElseThrow();
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

        Book insertedBook = bookRepositoryJPA.save(newBook);

        entityManager.detach(insertedBook);

        Book findedBook = bookRepositoryJPA.findById(7).orElseThrow();
        var findedAuthor = findedBook.getAuthor();
        var findedGenre = findedBook.getGenre();

        assertEquals(7, findedBook.getId());
        assertEquals("new Test book title", findedBook.getTitle());
        assertEquals("test author 2", findedAuthor.getName());
        assertEquals("test genre 1", findedGenre.getName());
    }

    @Test
    void givenNewBookWithIdWhenSaveThenSaved() {
        var book = bookRepositoryJPA.findById(1).orElseThrow();
        var newAuthor = new Author(2, "test author 2");
        var newGenre = new Genre(2, "new Genre 2");
        book.setTitle("NewBook Title");
        book.setAuthor(newAuthor);
        book.setGenre(newGenre);
        bookRepositoryJPA.save(book);
        entityManager.flush();
        entityManager.detach(book);

        Book resultBook = bookRepositoryJPA.findById(1).orElseThrow();
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
        var book = bookRepositoryJPA.findById(1).orElseThrow();
        newComment.setBook(book);
        book.getComments().add(newComment);
        bookRepositoryJPA.save(book);
        entityManager.detach(book);

        var updatedBook = bookRepositoryJPA.findById(1).orElseThrow();
        var comments = updatedBook.getComments();
        var lastComment = comments.get(comments.size() - 1);

        assertEquals(3, comments.size());
        assertEquals("New comment test", lastComment.getText());
    }

    @Test
    void givenRemovedCommentWhenSaveThenSavedSuccessfully(){
        var book = bookRepositoryJPA.findById(1).orElseThrow();
        var comments = book.getComments();
        assertEquals(2, comments.size());
        var comment = comments.get(0);
        comments.remove(comment);
        comment.setBook(null);

        bookRepositoryJPA.save(book);
        entityManager.flush();
        entityManager.detach(book);

        var updatedBook = bookRepositoryJPA.findById(1).orElseThrow();
        var updatedComments = updatedBook.getComments();
        assertEquals(1, updatedComments.size());
    }

    @Test
    void givenIdWhenDeleteThenDeleted() {
        var book  = bookRepositoryJPA.findById(1).orElseThrow();
        bookRepositoryJPA.delete(book);
        assertTrue(bookRepositoryJPA.findById(1).isEmpty());
    }
}