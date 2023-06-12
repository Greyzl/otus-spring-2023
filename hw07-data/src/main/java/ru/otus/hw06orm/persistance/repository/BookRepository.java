package ru.otus.hw06orm.persistance.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw06orm.persistance.entity.Author;
import ru.otus.hw06orm.persistance.entity.Book;
import ru.otus.hw06orm.persistance.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author","genre"})
    @Override
    List<Book> findAll();

    @EntityGraph(attributePaths = {"genre"})
    List<Book> findBookByAuthor(Author author);

    @EntityGraph(attributePaths = {"author"})
    List<Book> findBookByGenre(Genre genre);

    @EntityGraph(attributePaths = {"author","genre"})
    Optional<Book> findBookByTitle(String title);

    @EntityGraph(attributePaths = {"author","genre"})
    Optional<Book> findById(long id);

    @EntityGraph(attributePaths = {"comments"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Book> findBookWithCommentsById(long id);
}
