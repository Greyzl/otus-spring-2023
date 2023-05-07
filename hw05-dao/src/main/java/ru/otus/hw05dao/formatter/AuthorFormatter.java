package ru.otus.hw05dao.formatter;

import ru.otus.hw05dao.entity.Author;

import java.util.List;

public interface AuthorFormatter {

    String format(Author author);

    String format(List<Author> authors);
}
