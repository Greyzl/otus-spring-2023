package ru.otus.hw06orm.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw06orm.persistance.entity.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);

    boolean existsByName(String name);
}
