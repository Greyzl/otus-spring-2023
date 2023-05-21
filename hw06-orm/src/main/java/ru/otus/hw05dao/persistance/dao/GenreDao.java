package ru.otus.hw05dao.persistance.dao;

import org.springframework.stereotype.Repository;
import ru.otus.hw05dao.entity.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreDao {
    List<Genre> getAll();

    Optional<Genre> getById(long id);

    Optional<Genre> findByName(String name);

    Genre insert(Genre author);

    void update(Genre author);

    void deleteById(long id);
}
