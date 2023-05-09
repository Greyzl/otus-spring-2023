package ru.otus.hw05dao.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw05dao.exception.AuthorAlreadyExistsException;
import ru.otus.hw05dao.exception.AuthorNotFoundException;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.formatter.AuthorFormatter;
import ru.otus.hw05dao.service.AuthorService;

@ShellComponent
public class AuthorCommands {

    private final AuthorFormatter authorFormatter;

    private final AuthorService authorService;

    public AuthorCommands(AuthorFormatter authorFormatter,
                          AuthorService authorService){
        this.authorFormatter = authorFormatter;
        this.authorService = authorService;
    }

    @ShellMethod(value = "Get all saved book authors", key = {"author-get-all", "aga"})
    public String getAllAuthors(){
        var authors = authorService.getAll();
        if (authors.size() == 0){
            return "Don't have any authors, yet";
        }
        return authorFormatter.format(authors);
    }

    @ShellMethod(value = "Get book author by id", key = {"author-by-id", "abi"})
    public String getAuthor(long id){
        try {
            var author = authorService.get(id).orElseThrow(AuthorNotFoundException::new);
            return authorFormatter.format(author);
        } catch (AuthorNotFoundException exception){
            return "Author with such id not found";
        }
    }

    @ShellMethod(value = "Save entered name as author", key = {"author-add", "aa"})
    public String addAuthor(String name){
        try {
            authorService.findByName(name).ifPresent(author -> {
                throw new AuthorAlreadyExistsException(author);
            });
            Author newAuthor = authorService.add(name);
            return "Author successfully saved. " + authorFormatter.format(newAuthor);
        } catch (AuthorAlreadyExistsException e){
            return "Such author already exists." + authorFormatter.format(e.getAuthor());
        }
    }

    @ShellMethod(value = "Update author name", key = {"author-update", "au"})
    public String updateAuthor(long id, String name){
        try {
            Author author = authorService.get(id).orElseThrow(AuthorNotFoundException::new);
            Author updatedAuthor = authorService.update(author, name);
            return "Author successfully updated. " + authorFormatter.format(updatedAuthor);
        }catch (AuthorNotFoundException exception){
            return "Author with such id not found";
        }
    }

    @ShellMethod(value = "Delete author by id", key = {"author-delete", "ad"})
    public String deleteAuthor(long id){
        authorService.deleteById(id);
        return "Author successfully deleted";
    }
}
