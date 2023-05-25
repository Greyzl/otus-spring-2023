package ru.otus.hw06orm.formatter;

import ru.otus.hw06orm.entity.Genre;

import java.util.List;

public interface GenreFormatter {
    String format(Genre genre);

    String format(List<Genre> genres);
}
