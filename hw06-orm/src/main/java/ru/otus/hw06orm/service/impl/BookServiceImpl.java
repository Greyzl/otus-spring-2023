package ru.otus.hw06orm.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw06orm.builder.BookBuilder;
import ru.otus.hw06orm.entity.Book;
import ru.otus.hw06orm.entity.Comment;
import ru.otus.hw06orm.exception.AuthorNotFoundException;
import ru.otus.hw06orm.exception.GenreNotFoundException;
import ru.otus.hw06orm.persistance.repository.BookRepository;
import ru.otus.hw06orm.service.AuthorService;
import ru.otus.hw06orm.service.BookService;
import ru.otus.hw06orm.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorService authorService,
                           GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public List<Book> findByAuthorName(String authorName) throws AuthorNotFoundException{
        var author = authorService.findByName(authorName).orElseThrow(AuthorNotFoundException::new);
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findByGenreName(String genreName) throws GenreNotFoundException {
        var genre = genreService.getByName(genreName).orElseThrow(GenreNotFoundException::new);
        return bookRepository.findByGenre(genre);
    }

    @Override
    public Optional<Book> get(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Book add(String title, String authorName, String genreName) {
        var author = authorService.getOrCreate(authorName);
        var genre = genreService.getOrCreate(genreName);
        var newBook = new Book(title, author, genre);
        return bookRepository.save(newBook);
    }

    @Override
    public Book update(Book book, String newTitle, String newAuthorName, String newGenreName) {
        BookBuilder builder = book.toBuilder();
        builder.setTitle(newTitle);
        if (!newAuthorName.equals("")){
            var author = authorService.getOrCreate(newAuthorName);
            builder.setAuthor(author);
        }
        if (!newGenreName.equals("")){
            var genre = genreService.getOrCreate(newGenreName);
            builder.setGenre(genre);
        }
        Book buildedBook = builder.build();
        bookRepository.save(buildedBook);
        return buildedBook;
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public void addComment(Book book, String commentText) {
        Comment newComment = new Comment(commentText);
        List<Comment> comments = book.getComments();
        comments.add(newComment);
        bookRepository.save(book);
    }

    @Override
    public Optional<Comment> getBookCommentByIndex(Book book, int commentIndex) {
        try {
            return Optional.ofNullable(book.getComments().get(commentIndex));
        } catch (IndexOutOfBoundsException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> getBookComments(Book book) {
        return book.getComments();
    }

    @Override
    public void removeComment(Book book, Comment comment) {
        List<Comment> comments = book.getComments();
        comments.remove(comment);
        comment.setBook(null);
        bookRepository.save(book);
    }
}
