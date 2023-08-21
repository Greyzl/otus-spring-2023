package ru.otus.hw08mongo.persistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08mongo.persistance.entity.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findByName(String name);

    boolean existsByName(String name);
}

