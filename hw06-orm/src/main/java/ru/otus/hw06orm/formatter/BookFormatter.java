package ru.otus.hw06orm.formatter;

import ru.otus.hw06orm.entity.Book;

import java.util.List;

public interface BookFormatter {
    String format(Book book);

    String format(List<Book> books);
}
