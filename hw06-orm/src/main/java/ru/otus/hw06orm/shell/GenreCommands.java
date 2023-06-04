package ru.otus.hw06orm.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw06orm.entity.Genre;
import ru.otus.hw06orm.exception.GenreAlreadyExistsException;
import ru.otus.hw06orm.exception.GenreNotFoundException;
import ru.otus.hw06orm.formatter.GenreFormatter;
import ru.otus.hw06orm.service.GenreService;

@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    private final GenreFormatter genreFormatter;

    public GenreCommands(GenreService genreService,
                         GenreFormatter genreFormatter){
        this.genreService = genreService;
        this.genreFormatter = genreFormatter;
    }

    @ShellMethod(value = "Get all added genres", key = {"genre-get-all", "gga"})
    public String getAllGenres(){
        var genres = genreService.getAll();
        return genreFormatter.format(genres);
    }

    @ShellMethod(value = "Get genre by it's id", key = {"genre-by-id", "gbi"})
    public String getGenreById(long id){
        try {
            var genre = genreService.get(id);
            return genreFormatter.format(genre);
        }catch (GenreNotFoundException e){
            return "Genre with such id is not found";
        }
    }

    @ShellMethod(value = "Add new genre", key = {"genre-add", "ga"})
    public String addGenre(String name){
        try {
            Genre newGenre = genreService.add(name);
            return "Genre successfully added. " + genreFormatter.format(newGenre);
        } catch (GenreAlreadyExistsException e){
            return "Such genre already exists. " + genreFormatter.format(e.getGenre());
        }
    }

    @ShellMethod(value = "Update genre", key = {"genre-update", "gu"})
    public String updateGenre(long id, String name){
        try {
            var updatedGenre = genreService.update(id, name);
            return "Genre successfully updated. " + genreFormatter.format(updatedGenre);
        }catch (GenreNotFoundException e){
            return "Genre with such id is not found";
        }
    }

    @ShellMethod(value = "Update delete", key = {"genre-delete", "gd"})
    public String deleteGenre(long id){
        try {
            genreService.delete(id);
            return "Genre successfully deleted. ";
        } catch (GenreNotFoundException e){
            return "Genre with such id is not found";
        }
    }
}
