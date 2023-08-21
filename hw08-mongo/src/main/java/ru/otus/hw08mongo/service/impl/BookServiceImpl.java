package ru.otus.hw08mongo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw08mongo.dto.BookDto;
import ru.otus.hw08mongo.persistance.entity.Book;
import ru.otus.hw08mongo.persistance.entity.Comment;
import ru.otus.hw08mongo.exception.AuthorNotFoundException;
import ru.otus.hw08mongo.exception.BookAlreadyExistsException;
import ru.otus.hw08mongo.exception.BookNotFoundException;
import ru.otus.hw08mongo.exception.CommentNotFoundException;
import ru.otus.hw08mongo.exception.GenreNotFoundException;
import ru.otus.hw08mongo.mapper.BookDtoMapper;
import ru.otus.hw08mongo.persistance.repository.BookRepository;
import ru.otus.hw08mongo.service.AuthorService;
import ru.otus.hw08mongo.service.BookService;
import ru.otus.hw08mongo.service.GenreService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookRepository bookRepository;

    private final BookDtoMapper bookDtoMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorService authorService,
                           GenreService genreService,
                           BookDtoMapper bookDtoMapper) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookDtoMapper = bookDtoMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream().map(bookDtoMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findByAuthorName(String authorName) throws AuthorNotFoundException {
        var author = authorService.findByName(authorName);
        return bookRepository.findBookByAuthor(author).stream().map(bookDtoMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findByGenreName(String genreName) throws GenreNotFoundException {
        var genre = genreService.getByName(genreName);
        return bookRepository.findBookByGenre(genre).stream().map(bookDtoMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto get(String id) throws BookNotFoundException {
        var book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookDtoMapper.toDto(book);
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findByTitle(String title) throws BookNotFoundException {
        var book = bookRepository.findBookByTitle(title).orElseThrow(BookNotFoundException::new);
        return bookDtoMapper.toDto(book);
    }

    @Transactional
    @Override
    public BookDto add(String title, String authorName, String genreName) throws BookAlreadyExistsException {
        var mayBeBook = bookRepository.findBookByTitle(title);
        mayBeBook.ifPresent(book -> {
            throw new BookAlreadyExistsException(bookDtoMapper.toDto(book));
        });
        var author = authorService.getOrCreate(authorName);
        var genre = genreService.getOrCreate(genreName);
        var newBook = new Book(title, author, genre);
        var savedBook = bookRepository.save(newBook);
        return bookDtoMapper.toDto(savedBook);
    }

    @Transactional
    @Override
    public BookDto update(String id, String newTitle, String newAuthorName, String newGenreName)
            throws BookNotFoundException {
        var book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        book.setTitle(newTitle);
        if (!newAuthorName.equals("")) {
            var author = authorService.getOrCreate(newAuthorName);
            book.setAuthor(author);
        }
        if (!newGenreName.equals("")) {
            var genre = genreService.getOrCreate(newGenreName);
            book.setGenre(genre);
        }
        var savedBook = bookRepository.save(book);
        return bookDtoMapper.toDto(savedBook);
    }

    @Transactional
    @Override
    public void delete(String id) throws BookNotFoundException {
        var book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteWithComments(book);
    }

    @Transactional
    @Override
    public void addComment(String bookId, String commentText) throws BookNotFoundException {
        var book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Comment newComment = new Comment(commentText);
        bookRepository.addComment(book, newComment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getBookComments(String bookId) throws BookNotFoundException {
        var book = bookRepository.findBookWithCommentsById(bookId).orElseThrow(BookNotFoundException::new);
        return book.getComments();
    }

    @Transactional
    @Override
    public void removeComment(String bookId, String commentId) throws BookNotFoundException, CommentNotFoundException {
        var book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        var comments = book.getComments();
        var searchedComment = comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst().orElseThrow(CommentNotFoundException::new);
        bookRepository.deleteComment(book, searchedComment);
    }
}
