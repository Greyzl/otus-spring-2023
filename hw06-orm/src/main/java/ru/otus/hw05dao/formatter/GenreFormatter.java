package ru.otus.hw05dao.formatter;

import ru.otus.hw05dao.entity.Genre;

import java.util.List;

public interface GenreFormatter {
    String format(Genre genre);

    String format(List<Genre> genres);
}
