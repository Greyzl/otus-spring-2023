package ru.otus.hw06orm.persistance.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw06orm.persistance.entity.Genre;
import ru.otus.hw06orm.persistance.repository.GenreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager entityManager;

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
        var genre = genreRepository.findById(1L).orElseThrow();
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
        assertEquals(4, genreWithId.getId());

        entityManager.detach(genreWithId);

        var genreWithIdFromDb = genreRepository.findById(4L).orElseThrow();
        assertEquals("New Genre name", genreWithIdFromDb.getName());
    }

    @Test
    void givenAuthorWithIdWhenSaveThenNewNameCorrect() {
        var genre = genreRepository.findById(2L).orElseThrow();
        genre.setName("New name genre 2");
        var updatedGenre = genreRepository.save(genre);
        entityManager.flush();
        entityManager.detach(updatedGenre);

        var updatedGenreFromDb = genreRepository.findById(2L).orElseThrow();

        assertEquals("New name genre 2", updatedGenreFromDb.getName());
    }


    @Test
    void givenGenreWhenDeleteThenGenreNotFound() {
        var genre = genreRepository.findById(3L).orElseThrow();
        genreRepository.delete(genre);
        entityManager.flush();
        entityManager.detach(genre);
        assertTrue(genreRepository.findById(3L).isEmpty());
    }

    @Test
    void givenNameWhenIsExistsThenReturnTrue(){
        boolean isExists = genreRepository.existsByName("test genre 2");
        assertTrue(isExists);
    }

    @Test
    void givenFakeNameWhenIsExistsThenReturnFalse(){
        boolean isExists = genreRepository.existsByName("test genre not exists");
        assertFalse(isExists);
    }
}