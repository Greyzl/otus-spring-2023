package ru.otus.hw06orm.persistance.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw06orm.entity.Author;
import ru.otus.hw06orm.persistance.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;


@Repository
public class AuthorRepositoryJPA implements AuthorRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public AuthorRepositoryJPA(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Author> getAll() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public Optional<Author> findByName(String name) {
        try {
            return Optional.ofNullable(entityManager.createQuery(
                            "select a from Author a where a.name = :name", Author.class)
                    .setParameter("name", name).getSingleResult());
        } catch (NoResultException e){
            return Optional.empty();
        }

    }

    @Override
    public Author save(Author author){
        if (author.getId() == 0){
            entityManager.persist(author);
            return author;
        } else {
            return entityManager.merge(author);
        }
    }

    @Override
    public void delete(Author author) {
        entityManager.remove(author);
    }
}
