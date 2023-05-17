package ru.otus.hw05dao.builder;

import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.entity.Genre;

public class BookBuilder {
    private long id;

    private String title;

    private Author author;

    private Genre genre;

    public BookBuilder(long id, String title, Author author, Genre genre){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public BookBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public BookBuilder setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public Book build(){
        return new Book(id, title, author, genre);
    }
}
