package ru.otus.hw06orm.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import ru.otus.hw06orm.builder.BookBuilder;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    private List<Comment> comments;

    public Book(){

    }

    public Book(String title, Author author, Genre genre){
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id, String title, Author author, Genre genre, List<Comment> comments){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public BookBuilder toBuilder(){
        return new BookBuilder(id, title, author, genre, comments);
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
