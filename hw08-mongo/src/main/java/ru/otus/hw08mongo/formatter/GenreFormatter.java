package ru.otus.hw08mongo.formatter;

import ru.otus.hw08mongo.persistance.entity.Genre;

import java.util.List;

public interface GenreFormatter {
    String format(Genre genre);

    String format(List<Genre> genres);
}
