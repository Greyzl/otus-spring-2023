package ru.otus.hw05dao.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw05dao.entity.Genre;
import ru.otus.hw05dao.formatter.GenreFormatter;

import java.util.List;

@Component
public class GenreFormatterImpl implements GenreFormatter {

    @Override
    public String format(Genre genre) {
        return String.format("id: %d. Genre: %s", genre.getId(), genre.getName());
    }

    @Override
    public String format(List<Genre> genres) {
        StringBuilder builder = new StringBuilder();
        for (Genre genre: genres){
            builder.append(format(genre)).append("\n");
        }
        return builder.toString();
    }
}
