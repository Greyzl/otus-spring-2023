package ru.otus.hw05dao.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw05dao.builder.BookBuilder;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.exception.AuthorNotFoundException;
import ru.otus.hw05dao.exception.GenreNotFoundException;
import ru.otus.hw05dao.persistance.repository.BookRepository;
import ru.otus.hw05dao.service.AuthorService;
import ru.otus.hw05dao.service.BookService;
import ru.otus.hw05dao.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

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
        return author.getBooks();
    }

    @Override
    public List<Book> findByGenreName(String genreName) throws GenreNotFoundException {
        var genre = genreService.getByName(genreName).orElseThrow(GenreNotFoundException::new);
        return genre.getBooks();
    }

    @Override
    public Optional<Book> get(long id) {
        return bookRepository.getById(id);
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
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
