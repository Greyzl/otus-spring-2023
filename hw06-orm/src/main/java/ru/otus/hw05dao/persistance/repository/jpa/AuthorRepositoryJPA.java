package ru.otus.hw05dao.persistance.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.persistance.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;


@Repository
public class AuthorRepositoryJPA implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Author> getAll() {
        return entityManager.createQuery("SELECT A FROM Author A", Author.class).getResultList();
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public Optional<Author> findByName(String name) {
        return entityManager.createQuery(
                "SELECT A FROM Author A WHERE A.name = :name", Author.class)
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
    public void deleteById(long id) {
        entityManager.createQuery("delete FROM Author where id = :id")
                .setParameter("id",id).executeUpdate();
    }
}
