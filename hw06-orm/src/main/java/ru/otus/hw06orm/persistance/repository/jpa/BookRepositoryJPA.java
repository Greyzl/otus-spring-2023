package ru.otus.hw06orm.persistance.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw06orm.entity.Author;
import ru.otus.hw06orm.entity.Book;
import ru.otus.hw06orm.entity.Comment;
import ru.otus.hw06orm.entity.Genre;
import ru.otus.hw06orm.persistance.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJPA implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookRepositoryJPA(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> getAll() {
        return entityManager.createQuery(
                "select b from Book b join fetch b.author join fetch b.genre", Book.class).getResultList();
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return entityManager.createQuery(
                "select b from Book b " +
                        "join fetch b.genre where b.author = :author", Book.class)
                .setParameter("author", author).getResultList();
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        return entityManager.createQuery(
                        "select b from Book b " +
                                "join fetch b.author where b.genre = :genre", Book.class)
                .setParameter("genre", genre).getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public Optional<Book> findByTitle(String name) {
        return entityManager.createQuery(
                "select b from Book b join fetch b.author join fetch b.genre " +
                        "left join fetch b.comments where b.title = :title", Book.class)
                .setParameter("title",name).getResultList().stream().findFirst();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0){
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public void delete(Book book) {
        entityManager.remove(book);
    }

    @Override
    public List<Comment> getComments(long bookId) {
        return entityManager.createQuery("select c from Comment c where c.book.id=:bookId",Comment.class)
                .setParameter("bookId", bookId).getResultList();
    }
}
