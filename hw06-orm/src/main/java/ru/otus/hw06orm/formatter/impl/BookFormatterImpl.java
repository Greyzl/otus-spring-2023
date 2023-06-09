package ru.otus.hw06orm.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw06orm.persistance.entity.Book;
import ru.otus.hw06orm.formatter.BookFormatter;

@Component
public class BookFormatterImpl implements BookFormatter {
    @Override
    public String format(Book book) {
        return String.format("id: %d. Book title: \"%s\"",
                book.getId(),
                book.getTitle());
    }
}
