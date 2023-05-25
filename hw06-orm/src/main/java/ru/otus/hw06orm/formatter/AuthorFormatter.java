package ru.otus.hw06orm.formatter;

import ru.otus.hw06orm.entity.Author;

import java.util.List;

public interface AuthorFormatter {

    String format(Author author);

    String format(List<Author> authors);
}
