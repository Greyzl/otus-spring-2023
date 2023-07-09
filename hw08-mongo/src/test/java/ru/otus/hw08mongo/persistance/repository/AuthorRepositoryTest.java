package ru.otus.hw08mongo.persistance.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw08mongo.AbstractRepositoryTest;
import ru.otus.hw08mongo.persistance.entity.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AuthorRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void beforeEach() {
        super.executeMongock();
    }

    @AfterEach
    void afterEach() {
        mongoTemplate.getDb().drop();
    }

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
        var authors = authorRepository.findAll();
        var author = authorRepository.findById(authors.get(1).getId()).orElseThrow();
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
        assertNotNull(authorWithId.getId());

        var authorWithIdFromDb = authorRepository.findById(authorWithId.getId()).orElseThrow();
        assertEquals("New Author name", authorWithIdFromDb.getName());
    }

    @Test
    void givenAuthorWithIdWhenSaveThenNewNameCorrect() {
        var authors = authorRepository.findAll();
        var author = authorRepository.findById(authors.get(1).getId()).orElseThrow();
        author.setName("New name author 2");
        var updatedAuthor = authorRepository.save(author);

        var updatedAuthorFromDb = authorRepository.findById(updatedAuthor.getId()).orElseThrow();

        assertEquals("New name author 2", updatedAuthorFromDb.getName());
    }


    @Test
    void givenAuthorWhenDeleteThenAuthorNotFound() {
        var authors = authorRepository.findAll();
        var author = authorRepository.findById(authors.get(4).getId()).orElseThrow();
        authorRepository.delete(author);
        assertTrue(authorRepository.findById(author.getId()).isEmpty());
    }

    @Test
    void givenNameWhenIsExistsThenReturnTrue() {
        boolean isExists = authorRepository.existsByName("test author 3");
        assertTrue(isExists);
    }

    @Test
    void givenFakeNameWhenIsExistsThenReturnFalse() {
        boolean isExists = authorRepository.existsByName("test author not exists");
        assertFalse(isExists);
    }
}