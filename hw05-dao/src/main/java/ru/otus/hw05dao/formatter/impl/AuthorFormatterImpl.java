package ru.otus.hw05dao.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.formatter.AuthorFormatter;

import java.util.List;

@Component
public class AuthorFormatterImpl implements AuthorFormatter {

    @Override
    public String format(Author author) {
        return String.format("id: %d. Author name: %s", author.getId(), author.getName());
    }

    @Override
    public String format(List<Author> authors) {
        StringBuilder builder = new StringBuilder();
        for (Author author: authors){
            builder.append(format(author));
            builder.append("\n");
        }
        return builder.toString();
    }
}
