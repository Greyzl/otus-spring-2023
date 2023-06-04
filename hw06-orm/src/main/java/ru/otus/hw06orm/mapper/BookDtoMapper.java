package ru.otus.hw06orm.mapper;

import org.springframework.stereotype.Component;
import ru.otus.hw06orm.dto.BookDto;
import ru.otus.hw06orm.entity.Author;
import ru.otus.hw06orm.entity.Book;
import ru.otus.hw06orm.entity.Genre;

@Component
public class BookDtoMapper {

    public BookDto toDto(Book book){
        Author author = book.getAuthor();
        Genre genre = book.getGenre();
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        if (author != null){
            bookDto.setAuthorName(author.getName());
        }
        if (genre != null ){
            bookDto.setGenreName(genre.getName());
        }
        bookDto.setTitle(book.getTitle());
        return bookDto;
    }
}
