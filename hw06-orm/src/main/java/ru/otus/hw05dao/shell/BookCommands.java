package ru.otus.hw05dao.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw05dao.entity.Book;
import ru.otus.hw05dao.exception.AuthorNotFoundException;
import ru.otus.hw05dao.exception.BookAlreadyExistsException;
import ru.otus.hw05dao.exception.BookNotFoundException;
import ru.otus.hw05dao.exception.GenreNotFoundException;
import ru.otus.hw05dao.formatter.BookFormatter;
import ru.otus.hw05dao.service.BookService;

@ShellComponent
public class BookCommands {

    private final BookFormatter bookFormatter;

    private final BookService bookService;

    public BookCommands(BookFormatter bookFormatter,
                        BookService bookService){
        this.bookFormatter = bookFormatter;
        this.bookService = bookService;
    }

    @ShellMethod(value = "Get all saved books", key = {"book-get-all", "bga"})
    public String getAllBooks(){
        var books = bookService.getAll();
        if (books.size() == 0){
            return "Doesn't have any book";
        }
        return bookFormatter.format(books);
    }

    @ShellMethod(value = "Get book by id", key = {"book-by-id", "bbi"})
    public String getBook(
            @ShellOption(value = {"-i", "--id"}, help = "Enter id of book") long id){
        try {
            var book = bookService.get(id).orElseThrow(BookNotFoundException::new);
            return bookFormatter.format(book);
        } catch (BookNotFoundException e){
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Get book by title", key = {"book-by-title", "bbt"})
    public String getBookByTitle(@ShellOption({"-t", "--title"}) String title){
        try {
            var book = bookService.findByTitle(title).orElseThrow(BookNotFoundException::new);
            return bookFormatter.format(book);
        } catch (BookNotFoundException e){
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Get book by author name", key = {"book-by-author", "bba"})
    public String getBooksByAuthor(@ShellOption({"-a", "--author-name"})String authorName){
        try {
            var books = bookService.findByAuthorName(authorName);
            return bookFormatter.format(books);
        } catch (BookNotFoundException e){
            return "Book with such id is not found";
        } catch (AuthorNotFoundException e){
            return "Such author is not found";
        }
    }

    @ShellMethod(value = "Get book by genre name", key = {"book-by-genre", "bbg"})
    public String getBooksByGenre(@ShellOption({"-g", "--genre-name"})String genreName){
        try {
            var books = bookService.findByGenreName(genreName);
            return bookFormatter.format(books);
        } catch (BookNotFoundException e){
            return "Book with such id is not found";
        } catch (GenreNotFoundException e){
            return "Such genre is not found";
        }
    }

    @ShellMethod(value = "Add new book. Enter book title, author name and genre",
            key = {"book-add", "ba"})
    public String addBook(
            @ShellOption({"-t", "--title"}) String title,
            @ShellOption({"-a", "--author-name"}) String authorName,
            @ShellOption({"-g", "--genre-name"}) String genreName){
        try {
            bookService.findByTitle(title).ifPresent(book -> {
                throw new BookAlreadyExistsException(book);
            });
            var newBook = bookService.add(title, authorName, genreName);
            return "Book successfully saved. " + bookFormatter.format(newBook);
        } catch (BookAlreadyExistsException e){
            return "Such book already added " + bookFormatter.format(e.getBook());

        }
    }

    @ShellMethod(value = "Update author name", key = {"book-update", "bu"})
    public String updateBook(@ShellOption({"-i", "--id"}) long id,
                             @ShellOption({"-t", "--title"}) String title,
                             @ShellOption(value = {"-a", "--author-name"},defaultValue = "") String authorName,
                             @ShellOption(value = {"-g", "--genre-name"},defaultValue = "") String genreName){
        try {
            var book = bookService.get(id).orElseThrow(BookNotFoundException::new);
            Book updatedBook = bookService.update(book, title, authorName, genreName);
            return "Author successfully updated. " + bookFormatter.format(updatedBook);
        } catch (BookNotFoundException e){
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Delete author by id", key = {"book-delete", "bd"})
    public String deleteAuthor(@ShellOption({"-i", "--id"}) long id){
        bookService.deleteById(id);
        return "Author successfully deleted";
    }

}
