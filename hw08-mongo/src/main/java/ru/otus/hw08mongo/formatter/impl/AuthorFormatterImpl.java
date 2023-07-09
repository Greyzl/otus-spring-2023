package ru.otus.hw08mongo.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw08mongo.persistance.entity.Author;
import ru.otus.hw08mongo.formatter.AuthorFormatter;

import java.util.List;

@Component
public class AuthorFormatterImpl implements AuthorFormatter {

    @Override
    public String format(Author author) {
        return String.format("id: %s. Author name: %s", author.getId(), author.getName());
    }

    @Override
    public String format(List<Author> authors) {
        StringBuilder builder = new StringBuilder();
        for (Author author: authors) {
            builder.append(format(author));
            builder.append("\n");
        }
        return builder.toString();
    }
}
