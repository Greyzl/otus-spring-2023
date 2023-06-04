package ru.otus.hw06orm.persistance.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw06orm.entity.Genre;
import ru.otus.hw06orm.persistance.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public GenreRepositoryJpa(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Genre> getAll() {
        return entityManager.createQuery(
                "select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> findByName(String name) {
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "select g from Genre g where g.name = :name", Genre.class)
                    .setParameter("name", name).getSingleResult());
        } catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0){
            entityManager.persist(genre);
            return genre;
        }
        return entityManager.merge(genre);
    }

    @Override
    public void delete(Genre genre) {
        entityManager.remove(genre);
    }
}
