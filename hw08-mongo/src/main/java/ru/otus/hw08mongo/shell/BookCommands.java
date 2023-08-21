package ru.otus.hw08mongo.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw08mongo.exception.AuthorNotFoundException;
import ru.otus.hw08mongo.exception.BookAlreadyExistsException;
import ru.otus.hw08mongo.exception.BookNotFoundException;
import ru.otus.hw08mongo.exception.CommentNotFoundException;
import ru.otus.hw08mongo.exception.GenreNotFoundException;
import ru.otus.hw08mongo.formatter.BookDtoFormatter;
import ru.otus.hw08mongo.formatter.CommentFormatter;
import ru.otus.hw08mongo.service.BookService;

@ShellComponent
public class BookCommands {

    private final BookDtoFormatter bookDtoFormatter;

    private final BookService bookService;

    private final CommentFormatter commentFormatter;

    public BookCommands(BookDtoFormatter bookDtoFormatter,
                        BookService bookService,
                        CommentFormatter commentFormatter) {
        this.bookDtoFormatter = bookDtoFormatter;
        this.bookService = bookService;
        this.commentFormatter = commentFormatter;
    }

    @ShellMethod(value = "Get all saved books", key = {"book-get-all", "bga"})
    public String getAllBooks() {
        var bookDtos = bookService.getAll();
        if (bookDtos.size() == 0) {
            return "Doesn't have any book";
        }
        return bookDtoFormatter.format(bookDtos);
    }

    @ShellMethod(value = "Get book by id", key = {"book-by-id", "bbi"})
    public String getBook(
            @ShellOption(value = {"-i", "--id"}, help = "Enter id of book") String id) {
        try {
            var bookDto = bookService.get(id);
            return bookDtoFormatter.format(bookDto);
        } catch (BookNotFoundException e) {
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Get book by title", key = {"book-by-title", "bbt"})
    public String getBookByTitle(@ShellOption({"-t", "--title"}) String title) {
        try {
            var bookDto = bookService.findByTitle(title);
            return bookDtoFormatter.format(bookDto);
        } catch (BookNotFoundException e) {
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Get book by author name", key = {"book-by-author", "bba"})
    public String getBooksByAuthor(@ShellOption({"-a", "--author-name"})String authorName) {
        try {
            var bookDtos = bookService.findByAuthorName(authorName);
            if (bookDtos.size() == 0) {
                return "Book with such author are not found";
            }
            return bookDtoFormatter.format(bookDtos);
        } catch (AuthorNotFoundException e) {
            return "Such author is not found";
        }
    }

    @ShellMethod(value = "Get book by genre name", key = {"book-by-genre", "bbg"})
    public String getBooksByGenre(@ShellOption({"-g", "--genre-name"})String genreName) {
        try {
            var bookDtos = bookService.findByGenreName(genreName);
            if (bookDtos.size() == 0) {
                return "Books with such genre are not found";
            }
            return bookDtoFormatter.format(bookDtos);
        } catch (GenreNotFoundException e) {
            return "Such genre is not found";
        }
    }

    @ShellMethod(value = "Add new book. Enter book title, author name and genre",
            key = {"book-add", "ba"})
    public String addBook(
            @ShellOption({"-t", "--title"}) String title,
            @ShellOption({"-a", "--author-name"}) String authorName,
            @ShellOption({"-g", "--genre-name"}) String genreName) {
        try {
            var newBookDto = bookService.add(title, authorName, genreName);
            return "Book successfully saved. " + bookDtoFormatter.format(newBookDto);
        } catch (BookAlreadyExistsException e) {
            return "Such book already added " + bookDtoFormatter.format(e.getBookDto());

        }
    }

    @ShellMethod(value = "Update book name, author name, genre name", key = {"book-update", "bu"})
    public String updateBook(@ShellOption({"-i", "--id"}) String id,
                             @ShellOption({"-t", "--title"}) String title,
                             @ShellOption(value = {"-a", "--author-name"},defaultValue = "") String authorName,
                             @ShellOption(value = {"-g", "--genre-name"},defaultValue = "") String genreName) {
        try {
            var bookDto = bookService.update(id, title, authorName, genreName);
            return "Book successfully updated. " + bookDtoFormatter.format(bookDto);
        } catch (BookNotFoundException e) {
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Delete book by id. Author and genres won't be deleted", key = {"book-delete", "bd"})
    public String deleteBook(@ShellOption({"-i", "--id"}) String id) {
        try {
            bookService.delete(id);
            return "Book successfully deleted";
        } catch (BookNotFoundException e) {
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Add comment to book", key = {"Book-comment-add", "bca"})
    public String addBookComment(@ShellOption({"-i", "--bookId"}) String bookId,
                             @ShellOption({"-c", "--comment"}) String commentText) {
        try {
            bookService.addComment(bookId, commentText);
            return "Comment successfully added";
        } catch (BookNotFoundException e) {
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Get book comments", key = {"Book-comment-get-all", "bcga"})
    public String getBookComments(@ShellOption({"-i", "--bookId"}) String bookId) {
        try {
            var comments = bookService.getBookComments(bookId);
            if (comments.size() == 0) {
                return "Comments are not found";
            }
            return commentFormatter.format(comments);
        } catch (BookNotFoundException e) {
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Delete book comment", key = {"Book-comment-delete", "bcd"})
    public String deleteBookComment(@ShellOption({"-i", "--bookId"}) String bookId,
                             @ShellOption({"-ci", "--commentId"}) String commentId) {
        try {
            bookService.removeComment(bookId, commentId);
            return "Comment successfully deleted";
        } catch (BookNotFoundException e) {
            return "Book with such id is not found";
        } catch (CommentNotFoundException e) {
            return "Comment with such index is not found";
        }
    }

}
