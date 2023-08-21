package ru.otus.hw08mongo.persistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08mongo.persistance.entity.Author;
import ru.otus.hw08mongo.persistance.entity.Book;
import ru.otus.hw08mongo.persistance.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    List<Book> findBookByAuthor(Author author);

    List<Book> findBookByGenre(Genre genre);

    Optional<Book> findBookByTitle(String title);

    Optional<Book> findBookWithCommentsById(String id);
}
