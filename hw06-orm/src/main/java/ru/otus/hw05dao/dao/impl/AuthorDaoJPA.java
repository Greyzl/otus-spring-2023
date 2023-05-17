package ru.otus.hw05dao.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.otus.hw05dao.dao.GenreDao;
import ru.otus.hw05dao.entity.Genre;

import java.util.List;
import java.util.Optional;


public class AuthorDaoJPA implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Genre insert(Genre author) {
        return null;
    }

    @Override
    public void update(Genre author) {

    }

    @Override
    public void deleteById(long id) {

    }
}
