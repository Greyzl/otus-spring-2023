package ru.otus.hw05dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.hw05dao.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapping implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("ID");
        String name = rs.getString("NAME");
        return new Genre(id, name);
    }
}
