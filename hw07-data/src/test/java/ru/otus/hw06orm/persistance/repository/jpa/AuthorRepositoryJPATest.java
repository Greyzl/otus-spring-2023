package ru.otus.hw06orm.persistance.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw06orm.persistance.entity.Author;
import ru.otus.hw06orm.persistance.repository.AuthorRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class AuthorRepositoryJPATest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void whenGetAllThenCorrectCount() {
        List<Author> authors = authorRepository.findAll();
        assertEquals(5 ,authors.size());
    }

    @Test
    void whenGetAllThenAuthorNameCorrect() {
        var authors = authorRepository.findAll();
        var lastAuthorIndex = authors.size() - 1;
        var lastAuthor = authors.get(lastAuthorIndex);
        assertEquals("test author 5" ,lastAuthor.getName());
    }

    @Test
    void givenIdWhenGetByIdThenAuthorNameCorrect() {
        var author = authorRepository.findById(2L).orElseThrow();
        assertEquals("test author 2", author.getName());
    }

    @Test
    void givenNameWhenFindByNameThenAuthorNameCorrect() {
        var author = authorRepository.findByName("test author 3").orElseThrow();
        assertEquals("test author 3", author.getName());
    }

    @Test
    void givenAuthorWithoutIdWhenSaveThenReturnAuthorWithId() {
        Author newAuthor = new Author("New Author name");
        var authorWithId = authorRepository.save(newAuthor);
        assertEquals(6, authorWithId.getId());

        entityManager.detach(authorWithId);

        var authorWithIdFromDb = authorRepository.findById(6L).orElseThrow();
        assertEquals("New Author name", authorWithIdFromDb.getName());
    }

    @Test
    void givenAuthorWithIdWhenSaveThenNewNameCorrect() {
        var author = authorRepository.findById(2L).orElseThrow();
        author.setName("New name author 2");
        var updatedAuthor = authorRepository.save(author);
        entityManager.flush();
        entityManager.detach(updatedAuthor);

        var updatedAuthorFromDb = authorRepository.findById(2L).orElseThrow();

        assertEquals("New name author 2", updatedAuthorFromDb.getName());
    }


    @Test
    void givenAuthorWhenDeleteThenAuthorNotFound() {
        var author = authorRepository.findById(5L).orElseThrow();
        authorRepository.delete(author);
        entityManager.flush();
        entityManager.detach(author);
        assertTrue(authorRepository.findById(5L).isEmpty());
    }

    @Test
    void givenNameWhenIsExistsThenReturnTrue(){
        boolean isExists = authorRepository.existsByName("test author 3");
        assertTrue(isExists);
    }

    @Test
    void givenFakeNameWhenIsExistsThenReturnFalse(){
        boolean isExists = authorRepository.existsByName("test author not exists");
        assertFalse(isExists);
    }
}