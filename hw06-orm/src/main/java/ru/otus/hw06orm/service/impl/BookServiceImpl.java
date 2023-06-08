package ru.otus.hw06orm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06orm.dto.BookDto;
import ru.otus.hw06orm.persistance.entity.Book;
import ru.otus.hw06orm.persistance.entity.Comment;
import ru.otus.hw06orm.exception.AuthorNotFoundException;
import ru.otus.hw06orm.exception.BookAlreadyExistsException;
import ru.otus.hw06orm.exception.BookNotFoundException;
import ru.otus.hw06orm.exception.CommentNotFoundException;
import ru.otus.hw06orm.exception.GenreNotFoundException;
import ru.otus.hw06orm.mapper.BookDtoMapper;
import ru.otus.hw06orm.persistance.repository.BookRepository;
import ru.otus.hw06orm.service.AuthorService;
import ru.otus.hw06orm.service.BookService;
import ru.otus.hw06orm.service.GenreService;

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
        return bookRepository.getAll().stream().map(bookDtoMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findByAuthorName(String authorName) throws AuthorNotFoundException{
        var author = authorService.findByName(authorName);
        return bookRepository.findByAuthor(author).stream().map(bookDtoMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findByGenreName(String genreName) throws GenreNotFoundException {
        var genre = genreService.getByName(genreName);
        return bookRepository.findByGenre(genre).stream().map(bookDtoMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto get(long id) throws BookNotFoundException {
        var book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookDtoMapper.toDto(book);
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findByTitle(String title) throws BookNotFoundException {
        var book = bookRepository.findByTitle(title).orElseThrow(BookNotFoundException::new);
        return bookDtoMapper.toDto(book);
    }

    @Transactional
    @Override
    public BookDto add(String title, String authorName, String genreName) throws BookAlreadyExistsException {
        var mayBeBook = bookRepository.findByTitle(title);
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
    public BookDto update(long id, String newTitle, String newAuthorName, String newGenreName)
            throws BookNotFoundException{
        var book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        book.setTitle(newTitle);
        if (!newAuthorName.equals("")){
            var author = authorService.getOrCreate(newAuthorName);
            book.setAuthor(author);
        }
        if (!newGenreName.equals("")){
            var genre = genreService.getOrCreate(newGenreName);
            book.setGenre(genre);
        }
        var savedBook = bookRepository.save(book);
        return bookDtoMapper.toDto(savedBook);
    }

    @Transactional
    @Override
    public void delete(long id) throws BookNotFoundException {
        var book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

    @Transactional
    @Override
    public void addComment(long bookId, String commentText) throws BookNotFoundException {
        var book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Comment newComment = new Comment(commentText);
        List<Comment> comments = book.getComments();
        comments.add(newComment);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getBookComments(long bookId) throws BookNotFoundException {
        bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        return bookRepository.getComments(bookId);
    }

    @Transactional
    @Override
    public void removeComment(long bookId, long commentId) throws BookNotFoundException, CommentNotFoundException {
        var book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        var comments = book.getComments();
        var searchedComment = comments.stream()
                .filter(comment -> comment.getId() == commentId).findFirst().orElseThrow(CommentNotFoundException::new);
        comments.remove(searchedComment);
        searchedComment.setBook(null);
        bookRepository.save(book);
    }
}
