package ru.otus.hw06orm.builder;

import ru.otus.hw06orm.entity.Author;
import ru.otus.hw06orm.entity.Book;
import ru.otus.hw06orm.entity.Comment;
import ru.otus.hw06orm.entity.Genre;

import java.util.List;

public class BookBuilder {
    private long id;

    private String title;

    private Author author;

    private Genre genre;

    private List<Comment> comments;

    public BookBuilder(long id, String title, Author author, Genre genre, List<Comment> comments){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
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

    public BookBuilder setComments(List<Comment> comments){
        this.comments = comments;
        return this;
    }

    public Book build(){
        return new Book(id, title, author, genre, comments);
    }
}
