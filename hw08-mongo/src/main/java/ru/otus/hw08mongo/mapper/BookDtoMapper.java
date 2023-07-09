package ru.otus.hw08mongo.mapper;

import org.springframework.stereotype.Component;
import ru.otus.hw08mongo.dto.BookDto;
import ru.otus.hw08mongo.persistance.entity.Author;
import ru.otus.hw08mongo.persistance.entity.Book;
import ru.otus.hw08mongo.persistance.entity.Genre;

@Component
public class BookDtoMapper {

    public BookDto toDto(Book book) {
        Author author = book.getAuthor();
        Genre genre = book.getGenre();
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        if (author != null) {
            bookDto.setAuthorName(author.getName());
        }
        if (genre != null) {
            bookDto.setGenreName(genre.getName());
        }
        bookDto.setTitle(book.getTitle());
        return bookDto;
    }
}
