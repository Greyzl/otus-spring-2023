package ru.otus.hw06orm.persistance.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw06orm.persistance.entity.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@Import(AuthorRepositoryJPA.class)
class AuthorRepositoryJPATest {

    @Autowired
    private AuthorRepositoryJPA authorRepositoryJPA;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void whenGetAllThenCorrectCount() {
        List<Author> authors = authorRepositoryJPA.getAll();
        assertEquals(5 ,authors.size());
    }

    @Test
    void whenGetAllThenAuthorNameCorrect() {
        var authors = authorRepositoryJPA.getAll();
        var lastAuthorIndex = authors.size() - 1;
        var lastAuthor = authors.get(lastAuthorIndex);
        assertEquals("test author 5" ,lastAuthor.getName());
    }

    @Test
    void givenIdWhenGetByIdThenAuthorNameCorrect() {
        var author = authorRepositoryJPA.findById(2).orElseThrow();
        assertEquals("test author 2", author.getName());
    }

    @Test
    void givenNameWhenFindByNameThenAuthorNameCorrect() {
        var author = authorRepositoryJPA.findByName("test author 3").orElseThrow();
        assertEquals("test author 3", author.getName());
    }

    @Test
    void givenAuthorWithoutIdWhenSaveThenReturnAuthorWithId() {
        Author newAuthor = new Author("New Author name");
        var authorWithId = authorRepositoryJPA.save(newAuthor);
        assertEquals(6, authorWithId.getId());

        entityManager.detach(authorWithId);

        var authorWithIdFromDb = authorRepositoryJPA.findById(6).orElseThrow();
        assertEquals("New Author name", authorWithIdFromDb.getName());
    }

    @Test
    void givenAuthorWithIdWhenSaveThenNewNameCorrect() {
        var author = authorRepositoryJPA.findById(2).orElseThrow();
        author.setName("New name author 2");
        var updatedAuthor = authorRepositoryJPA.save(author);
        entityManager.flush();
        entityManager.detach(updatedAuthor);

        var updatedAuthorFromDb = authorRepositoryJPA.findById(2).orElseThrow();

        assertEquals("New name author 2", updatedAuthorFromDb.getName());
    }


    @Test
    void givenAuthorWhenDeleteThenAuthorNotFound() {
        var author = authorRepositoryJPA.findById(5).orElseThrow();
        authorRepositoryJPA.delete(author);
        entityManager.flush();
        entityManager.detach(author);
        assertTrue(authorRepositoryJPA.findById(5).isEmpty());
    }

    @Test
    void givenNameWhenIsExistsThenReturnTrue(){
        boolean isExists = authorRepositoryJPA.isExists("test author 3");
        assertTrue(isExists);
    }

    @Test
    void givenFakeNameWhenIsExistsThenReturnFalse(){
        boolean isExists = authorRepositoryJPA.isExists("test author not exists");
        assertFalse(isExists);
    }
}