package ru.otus.hw06orm.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw06orm.entity.Book;
import ru.otus.hw06orm.exception.AuthorNotFoundException;
import ru.otus.hw06orm.exception.BookAlreadyExistsException;
import ru.otus.hw06orm.exception.BookNotFoundException;
import ru.otus.hw06orm.exception.CommentNotFoundException;
import ru.otus.hw06orm.exception.GenreNotFoundException;
import ru.otus.hw06orm.formatter.BookFormatter;
import ru.otus.hw06orm.formatter.CommentFormatter;
import ru.otus.hw06orm.service.BookService;

@ShellComponent
public class BookCommands {

    private final BookFormatter bookFormatter;

    private final BookService bookService;

    private final CommentFormatter commentFormatter;

    public BookCommands(BookFormatter bookFormatter,
                        BookService bookService,
                        CommentFormatter commentFormatter){
        this.bookFormatter = bookFormatter;
        this.bookService = bookService;
        this.commentFormatter = commentFormatter;
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
            if (books.size() == 0){
                return "Book with such author are not found";
            }
            return bookFormatter.format(books);
        } catch (AuthorNotFoundException e){
            return "Such author is not found";
        }
    }

    @ShellMethod(value = "Get book by genre name", key = {"book-by-genre", "bbg"})
    public String getBooksByGenre(@ShellOption({"-g", "--genre-name"})String genreName){
        try {
            var books = bookService.findByGenreName(genreName);
            if (books.size() == 0){
                return "Books with such genre are not found";
            }
            return bookFormatter.format(books);
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
    public String deleteBook(@ShellOption({"-i", "--id"}) long id){
        try {
            var book = bookService.get(id).orElseThrow(BookNotFoundException::new);
            bookService.delete(book);
            return "Author successfully deleted";
        } catch (BookNotFoundException e) {
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Add comment to book", key = {"Book-comment-add", "bca"})
    public String addBookComment(@ShellOption({"-i", "--bookId"}) long bookId,
                             @ShellOption({"-c", "--comment"}) String commentText){
        try {
            var book = bookService.get(bookId).orElseThrow(BookNotFoundException::new);
            bookService.addComment(book, commentText);
            return "Comment successfully added";
        } catch (BookNotFoundException e){
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Get book comments", key = {"Book-comment-get-all", "bcga"})
    public String getBookComments(@ShellOption({"-i", "--bookId"}) long bookId){
        try {
            var book = bookService.get(bookId).orElseThrow(BookNotFoundException::new);
            var comments = bookService.getBookComments(book);
            if (comments.size() == 0){
                return "Comments are not found";
            }
            return commentFormatter.format(book.getComments());
        } catch (BookNotFoundException e){
            return "Book with such id is not found";
        }
    }

    @ShellMethod(value = "Delete book comment", key = {"Book-comment-delete", "bcd"})
    public String deleteBookComment(@ShellOption({"-i", "--bookId"}) long bookId,
                             @ShellOption({"-ci", "--commentIndex"}) int commentIndex){
        try {
            commentIndex--;
            var book = bookService.get(bookId).orElseThrow(BookNotFoundException::new);
            var searchedComment = bookService.getBookCommentByIndex(book,
                    commentIndex).orElseThrow(CommentNotFoundException::new);
            bookService.removeComment(book, searchedComment);
            return "Comment successfully deleted";
        } catch (BookNotFoundException e){
            return "Book with such id is not found";
        } catch (CommentNotFoundException e){
            return "Comment with such index is not found";
        }
    }

}
