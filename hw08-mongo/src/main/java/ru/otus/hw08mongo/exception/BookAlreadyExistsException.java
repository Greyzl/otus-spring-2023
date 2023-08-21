package ru.otus.hw08mongo.exception;

import ru.otus.hw08mongo.dto.BookDto;

public class BookAlreadyExistsException extends RuntimeException {
    private final BookDto bookDto;

    public BookAlreadyExistsException(BookDto bookDto) {
        super();
        this.bookDto = bookDto;
    }

    public BookDto getBookDto() {
        return bookDto;
    }
}
