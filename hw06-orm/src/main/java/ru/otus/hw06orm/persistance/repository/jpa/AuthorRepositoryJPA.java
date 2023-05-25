package ru.otus.hw06orm.persistance.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06orm.entity.Author;
import ru.otus.hw06orm.persistance.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;


@Repository
public class AuthorRepositoryJPA implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findById(long id) {
        return entityManager.createQuery(
                        "select a from Author a where a.id = :id", Author.class)
                .setParameter("id", id).getResultList().stream().findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findByName(String name) {
        return entityManager.createQuery(
                "select a from Author a where a.name = :name", Author.class)
                .setParameter("name", name).getResultList().stream().findFirst();
    }

    @Transactional
    @Override
    public Author save(Author author){
        if (author.getId() == 0){
            Author authorWithId = author.toBuilder().build();
            entityManager.persist(authorWithId);
            return authorWithId;
        } else {
            return entityManager.merge(author);
        }
    }

    @Transactional
    @Override
    public void delete(Author author) {
        entityManager.remove(author);
    }
}
