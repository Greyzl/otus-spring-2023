package ru.otus.hw05dao.entity;

import ru.otus.hw05dao.builder.BookBuilder;

import java.util.Objects;

public class Book {

    private long id;

    private final String title;

    private final Author author;

    private final Genre genre;

    public Book(String title, Author author, Genre genre){
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id, String title, Author author, Genre genre){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getAuthorId(){
        return author.getId();
    }

    public String getAuthorName() {
        return author.getName();
    }

    public long getGenreId(){
        return genre.getId();
    }

    public String getGenreName() {
        return genre.getName();
    }

    public BookBuilder toBuilder(){
        return new BookBuilder(id, title, author, genre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author.getName() +
                ", genre=" + genre.getName() +
                '}';
    }
}
