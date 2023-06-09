package ru.otus.hw06orm.persistance.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw06orm.persistance.entity.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void whenGetAllThenCorrectCount() {
        List<Genre> authors = genreRepositoryJpa.getAll();
        assertEquals(3 ,authors.size());
    }

    @Test
    void whenGetAllThenGenreNameCorrect() {
        var genres = genreRepositoryJpa.getAll();
        var lastGenreIndex = genres.size() - 1;
        var lastGenre = genres.get(lastGenreIndex);
        assertEquals("test genre 3" ,lastGenre.getName());
    }

    @Test
    void givenIdWhenGetByIdThenGenreNameCorrect() {
        var genre = genreRepositoryJpa.getById(1).orElseThrow();
        assertEquals("test genre 1", genre.getName());
    }

    @Test
    void givenNameWhenFindByNameThenGenreNameCorrect() {
        var genre = genreRepositoryJpa.findByName("test genre 1").orElseThrow();
        assertEquals("test genre 1", genre.getName());
    }

    @Test
    void givenGenreWithoutIdWhenSaveThenReturnGenreWithId() {
        Genre newGenre = new Genre("New Genre name");
        var genreWithId = genreRepositoryJpa.save(newGenre);
        assertEquals(4, genreWithId.getId());

        entityManager.detach(genreWithId);

        var genreWithIdFromDb = genreRepositoryJpa.getById(4).orElseThrow();
        assertEquals("New Genre name", genreWithIdFromDb.getName());
    }

    @Test
    void givenAuthorWithIdWhenSaveThenNewNameCorrect() {
        var genre = genreRepositoryJpa.getById(2).orElseThrow();
        genre.setName("New name genre 2");
        var updatedGenre = genreRepositoryJpa.save(genre);
        entityManager.flush();
        entityManager.detach(updatedGenre);

        var updatedGenreFromDb = genreRepositoryJpa.getById(2).orElseThrow();

        assertEquals("New name genre 2", updatedGenreFromDb.getName());
    }


    @Test
    void givenGenreWhenDeleteThenGenreNotFound() {
        var genre = genreRepositoryJpa.getById(3).orElseThrow();
        genreRepositoryJpa.delete(genre);
        entityManager.flush();
        entityManager.detach(genre);
        assertTrue(genreRepositoryJpa.getById(3).isEmpty());
    }

    @Test
    void givenNameWhenIsExistsThenReturnTrue(){
        boolean isExists = genreRepositoryJpa.isExists("test genre 2");
        assertTrue(isExists);
    }

    @Test
    void givenFakeNameWhenIsExistsThenReturnFalse(){
        boolean isExists = genreRepositoryJpa.isExists("test genre not exists");
        assertFalse(isExists);
    }
}