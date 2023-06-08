package ru.otus.hw06orm.formatter;

import ru.otus.hw06orm.persistance.entity.Book;

public interface BookFormatter {
    String format(Book book);
}
