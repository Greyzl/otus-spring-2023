package ru.otus.hw08mongo.persistance.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw08mongo.persistance.entity.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenreRepositoryJpaTest extends AuthorRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

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
        List<Genre> authors = genreRepository.findAll();
        assertEquals(3 ,authors.size());
    }

    @Test
    void whenGetAllThenGenreNameCorrect() {
        var genres = genreRepository.findAll();
        var lastGenreIndex = genres.size() - 1;
        var lastGenre = genres.get(lastGenreIndex);
        assertEquals("test genre 3" ,lastGenre.getName());
    }

    @Test
    void givenIdWhenGetByIdThenGenreNameCorrect() {
        var genres = genreRepository.findAll();
        var genre = genreRepository.findById(genres.get(0).getId()).orElseThrow();
        assertEquals("test genre 1", genre.getName());
    }

    @Test
    void givenNameWhenFindByNameThenGenreNameCorrect() {
        var genre = genreRepository.findByName("test genre 1").orElseThrow();
        assertEquals("test genre 1", genre.getName());
    }

    @Test
    void givenGenreWithoutIdWhenSaveThenReturnGenreWithId() {
        Genre newGenre = new Genre("New Genre name");
        var genreWithId = genreRepository.save(newGenre);
        assertNotNull(genreWithId.getId());

        var genreWithIdFromDb = genreRepository.findById(genreWithId.getId()).orElseThrow();
        assertEquals("New Genre name", genreWithIdFromDb.getName());
    }

    @Test
    void givenAuthorWithIdWhenSaveThenNewNameCorrect() {
        var genres = genreRepository.findAll();
        var genreId = genres.get(1).getId();
        var genre = genreRepository.findById(genreId).orElseThrow();
        genre.setName("New name genre 2");
        genreRepository.save(genre);

        var updatedGenreFromDb = genreRepository.findById(genreId).orElseThrow();

        assertEquals("New name genre 2", updatedGenreFromDb.getName());
    }


    @Test
    void givenGenreWhenDeleteThenGenreNotFound() {
        var genres = genreRepository.findAll();
        var genreId = genres.get(2).getId();
        var genre = genreRepository.findById(genreId).orElseThrow();
        genreRepository.delete(genre);
        assertTrue(genreRepository.findById(genreId).isEmpty());
    }

    @Test
    void givenNameWhenIsExistsThenReturnTrue() {
        boolean isExists = genreRepository.existsByName("test genre 2");
        assertTrue(isExists);
    }

    @Test
    void givenFakeNameWhenIsExistsThenReturnFalse() {
        boolean isExists = genreRepository.existsByName("test genre not exists");
        assertFalse(isExists);
    }
}