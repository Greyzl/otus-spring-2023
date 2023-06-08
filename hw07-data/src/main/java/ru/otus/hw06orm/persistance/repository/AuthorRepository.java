package ru.otus.hw06orm.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw06orm.persistance.entity.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

    boolean existsByName(String name);
}

