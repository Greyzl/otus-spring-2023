package ru.otus.hw06orm.persistance.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw06orm.entity.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
        var authorWithNewName = author.toBuilder().setName("New name author 2").build();
        var updatedAuthor = authorRepositoryJPA.save(authorWithNewName);
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
}