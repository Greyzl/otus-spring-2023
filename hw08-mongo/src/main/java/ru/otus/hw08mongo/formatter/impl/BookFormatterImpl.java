package ru.otus.hw08mongo.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw08mongo.persistance.entity.Book;
import ru.otus.hw08mongo.formatter.BookFormatter;

@Component
public class BookFormatterImpl implements BookFormatter {
    @Override
    public String format(Book book) {
        return String.format("id: %s. Book title: \"%s\"",
                book.getId(),
                book.getTitle());
    }
}
