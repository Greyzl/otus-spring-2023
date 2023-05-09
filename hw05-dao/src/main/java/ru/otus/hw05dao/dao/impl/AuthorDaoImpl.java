package ru.otus.hw05dao.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw05dao.dao.AuthorDao;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.exception.FailedToInsertAuthorException;
import ru.otus.hw05dao.mapper.AuthorMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations){
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    public List<Author> getAll(){
        return namedParameterJdbcOperations.query(
                "SELECT * FROM AUTHORS",new AuthorMapper());
    }

    @Override
    public Optional<Author> getById(long id) {
        return namedParameterJdbcOperations.query(
                "SELECT * FROM AUTHORS WHERE ID = :Id",
                Map.of("Id", id),new AuthorMapper()).stream().findFirst();
    }

    @Override
    public Optional<Author> findByName(String name) {
        return namedParameterJdbcOperations.query(
                "SELECT * FROM AUTHORS WHERE NAME = :name",
                Map.of("name", name),new AuthorMapper()).stream().findFirst();
    }

    @Override
    public Author insert(Author author){
        String name = author.getName();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", name);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "INSERT INTO AUTHORS (NAME) VALUES (:name)",
                mapSqlParameterSource,keyHolder,new String[]{"id"});
        Number key = Optional.ofNullable(keyHolder.getKey())
                .orElseThrow(FailedToInsertAuthorException::new);
        long id = key.longValue();
        return author.toBuilder().setId(id).build();
    }

    @Override
    public void update(Author author) {
        long id = author.getId();
        String name = author.getName();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        mapSqlParameterSource.addValue("name", name);
        namedParameterJdbcOperations.update(
                "UPDATE AUTHORS SET NAME = :name WHERE ID = :id", mapSqlParameterSource);
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update(
                "DELETE FROM AUTHORS WHERE ID = :id", Map.of("id",id));

    }
}
