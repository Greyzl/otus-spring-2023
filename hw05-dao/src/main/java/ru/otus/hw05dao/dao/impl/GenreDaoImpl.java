package ru.otus.hw05dao.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw05dao.dao.GenreDao;
import ru.otus.hw05dao.entity.Genre;
import ru.otus.hw05dao.exception.FailedToInsertGenreException;
import ru.otus.hw05dao.mapper.GenreMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations){
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("SELECT * FROM GENRES", new GenreMapping());

    }

    @Override
    public Optional<Genre> getById(long id) {
        return namedParameterJdbcOperations.query(
                "SELECT * FROM GENRES WHERE ID = :id",
                Map.of("id", id),new GenreMapping()).stream().findFirst();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return namedParameterJdbcOperations.query(
                "SELECT * FROM GENRES WHERE NAME = :name",
                Map.of("name", name),new GenreMapping()).stream().findFirst();
    }

    @Override
    public Genre insert(Genre genre) {
        String name = genre.getName();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", name);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "INSERT INTO GENRES (NAME) VALUES (:name)",mapSqlParameterSource,keyHolder, new String[]{"ID"});
        Number key = Optional.ofNullable(keyHolder.getKey())
                .orElseThrow(FailedToInsertGenreException::new);
        long id = key.longValue();
        return genre.toBuilder().setId(id).build();
    }

    @Override
    public void update(Genre genre) {
        long id = genre.getId();
        String name = genre.getName();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        mapSqlParameterSource.addValue("name", name);
        namedParameterJdbcOperations.update(
                "UPDATE GENRES SET NAME = :name WHERE ID = :id",mapSqlParameterSource);
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update("DELETE FROM GENRES WHERE ID = :id", Map.of("id", id));
    }
}
