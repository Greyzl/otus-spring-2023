package ru.otus.hw05dao.persistance.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.hw05dao.entity.Genre;
import ru.otus.hw05dao.persistance.repository.GenreRepository;

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
        return Optional.ofNullable(entityManager.find(Genre.class, id));
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
    public void deleteById(long id) {
        entityManager.createQuery(
                "delete from Genre g where g.id = :id")
                .setParameter("id",id);
    }
}
