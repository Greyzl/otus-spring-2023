package ru.otus.hw08mongo.formatter;

import ru.otus.hw08mongo.persistance.entity.Book;

public interface BookFormatter {
    String format(Book book);
}
