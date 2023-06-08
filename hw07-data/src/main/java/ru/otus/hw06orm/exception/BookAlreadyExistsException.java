package ru.otus.hw06orm.exception;

import ru.otus.hw06orm.dto.BookDto;

public class BookAlreadyExistsException extends RuntimeException{
    private final BookDto bookDto;

    public BookAlreadyExistsException(BookDto bookDto) {
        super();
        this.bookDto = bookDto;
    }

    public BookDto getBookDto() {
        return bookDto;
    }
}
