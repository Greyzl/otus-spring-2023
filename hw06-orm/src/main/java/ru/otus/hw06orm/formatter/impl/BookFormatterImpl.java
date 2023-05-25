package ru.otus.hw06orm.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw06orm.entity.Book;
import ru.otus.hw06orm.formatter.BookFormatter;

import java.util.List;

@Component
public class BookFormatterImpl implements BookFormatter {
    @Override
    public String format(Book book) {
        return String.format("id: %d. Book title: \"%s\", by: %s. Genre: %s",
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getGenre().getName());
    }

    @Override
    public String format(List<Book> books) {
        StringBuilder builder = new StringBuilder();
        for (Book book : books){
            builder.append(format(book)).append("\n");
        }
        return builder.toString();
    }
}
