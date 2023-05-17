package ru.otus.hw05dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.hw05dao.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("ID");
        String name = rs.getString("NAME");
        return new Author(id, name);
    }
}
