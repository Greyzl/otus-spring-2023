package ru.otus.hw05dao.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
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

    @ShellMethod
    public String getAllAuthors(){
        var authors = authorService.getAll();
        return authorFormatter.format(authors);
    }
}
