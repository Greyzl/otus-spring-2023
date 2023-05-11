package ru.otus.hw05dao.formatter;

import ru.otus.hw05dao.entity.Book;

import java.util.List;

public interface BookFormatter {
    String format(Book book);

    String format(List<Book> books);
}
