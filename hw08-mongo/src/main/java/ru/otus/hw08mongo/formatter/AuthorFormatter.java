package ru.otus.hw08mongo.formatter;

import ru.otus.hw08mongo.persistance.entity.Author;

import java.util.List;

public interface AuthorFormatter {

    String format(Author author);

    String format(List<Author> authors);
}
