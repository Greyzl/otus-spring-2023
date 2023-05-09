package ru.otus.hw05dao.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.formatter.BookFormatter;

import java.util.List;

@Component
public class BookFormatterImpl implements BookFormatter {
    @Override
    public String format(Book book) {
        return String.format("id: %d. Book title: \"%s\", by: %s. Genre: %s",
                book.getId(),
                book.getTitle(),
                book.getAuthorName(),
                book.getGenreName());
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
