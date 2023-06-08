package ru.otus.hw06orm.formatter;

import org.springframework.stereotype.Component;
import ru.otus.hw06orm.dto.BookDto;

import java.util.List;

@Component
public class BookDtoFormatter {
    public String format(BookDto bookDto) {
        StringBuilder builder = new StringBuilder();
        builder.append("id: ").append(bookDto.getId());
        builder.append(" Book title: ").append(bookDto.getTitle());
        if (!"".equals(bookDto.getAuthorName())) {
            builder.append(" by: ").append(bookDto.getAuthorName());
        }
        if (!"".equals(bookDto.getGenreName())) {
            builder.append(" Genre: ").append(bookDto.getGenreName());
        }
        return builder.toString();
    }

    public String format(List<BookDto> bookDtos) {
        StringBuilder builder = new StringBuilder();
        for (BookDto bookDto : bookDtos){
            builder.append(format(bookDto)).append("\n");
        }
        return builder.toString();
    }
}

