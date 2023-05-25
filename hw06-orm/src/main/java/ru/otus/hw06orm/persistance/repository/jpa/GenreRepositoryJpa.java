package ru.otus.hw06orm.persistance.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.hw06orm.entity.Genre;
import ru.otus.hw06orm.persistance.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Genre> getAll() {
        return entityManager.createQuery(
                "select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    public Optional<Genre> getById(long id) {
        return entityManager.createQuery(
                        "select g from Genre g where g.id = :id", Genre.class)
                .setParameter("id", id).getResultList().stream().findFirst();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return entityManager.createQuery(
                "select g from Genre g where g.name = :name", Genre.class)
                .setParameter("name", name).getResultList().stream().findFirst();
    }

    @Transactional
    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0){
            Genre genreWithId = genre.toBuilder().build();
            entityManager.persist(genreWithId);
            return genreWithId;
        }
        return entityManager.merge(genre);
    }

    @Transactional
    @Override
    public void delete(Genre genre) {
        entityManager.remove(genre);
    }
}
