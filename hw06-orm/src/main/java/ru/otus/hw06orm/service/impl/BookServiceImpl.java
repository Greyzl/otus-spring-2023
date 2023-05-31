package ru.otus.hw06orm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findByAuthorName(String authorName) throws AuthorNotFoundException{
        var author = authorService.findByName(authorName).orElseThrow(AuthorNotFoundException::new);
        return bookRepository.findByAuthor(author);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findByGenreName(String genreName) throws GenreNotFoundException {
        var genre = genreService.getByName(genreName).orElseThrow(GenreNotFoundException::new);
        return bookRepository.findByGenre(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> get(long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Transactional
    @Override
    public Book add(String title, String authorName, String genreName) {
        var author = authorService.getOrCreate(authorName);
        var genre = genreService.getOrCreate(genreName);
        var newBook = new Book(title, author, genre);
        return bookRepository.save(newBook);
    }

    @Transactional
    @Override
    public Book update(Book book, String newTitle, String newAuthorName, String newGenreName) {
        book.setTitle(newTitle);
        if (!newAuthorName.equals("")){
            var author = authorService.getOrCreate(newAuthorName);
            book.setAuthor(author);
        }
        if (!newGenreName.equals("")){
            var genre = genreService.getOrCreate(newGenreName);
            book.setGenre(genre);
        }
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Transactional
    @Override
    public void addComment(Book book, String commentText) {
        Comment newComment = new Comment(commentText);
        List<Comment> comments = book.getComments();
        comments.add(newComment);
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public Optional<Comment> getBookCommentByIndex(Book book, int commentIndex) {
        try {
            return Optional.ofNullable(book.getComments().get(commentIndex));
        } catch (IndexOutOfBoundsException e){
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getBookComments(Book book) {
        return book.getComments();
    }

    @Transactional
    @Override
    public void removeComment(Book book, Comment comment) {
        List<Comment> comments = book.getComments();
        comments.remove(comment);
        comment.setBook(null);
        bookRepository.save(book);
    }
}
