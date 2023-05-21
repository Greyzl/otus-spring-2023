package ru.otus.hw05dao.persistance.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.persistance.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJPA implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> getAll() {
        return entityManager.createQuery(
                "select b from Book b", Book.class).getResultList();
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public Optional<Book> findByTitle(String name) {
        return entityManager.createQuery(
                "select b from Book b where b.title = :title", Book.class)
                .setParameter("title",name).getResultList().stream().findFirst();
    }

    @Transactional
    @Override
    public Book save(Book book) {
        if (book.getId() == 0){
            Book bookWithId = book.toBuilder().build();
            entityManager.persist(bookWithId);
            return bookWithId;
        }
        return entityManager.merge(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        entityManager.createQuery("delete from Book b where b.id = :id")
                .setParameter("id", id).executeUpdate();
    }
}
