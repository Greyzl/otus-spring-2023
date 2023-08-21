package ru.otus.hw08mongo.persistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08mongo.persistance.entity.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

    Optional<Genre> findByName(String name);

    boolean existsByName(String name);
}
