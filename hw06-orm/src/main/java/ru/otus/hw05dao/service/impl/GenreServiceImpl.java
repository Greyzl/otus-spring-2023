package ru.otus.hw05dao.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw05dao.persistance.dao.GenreDao;
import ru.otus.hw05dao.entity.Genre;
import ru.otus.hw05dao.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao){
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Optional<Genre> get(long id) {
        return genreDao.getById(id);
    }

    @Override
    public Optional<Genre> getByName(String name) {
        return genreDao.findByName(name);
    }

    @Override
    public Genre getOrCreate(String name) {
        return getByName(name).orElse(add(name));
    }

    @Override
    public Genre add(String name) {
        Genre genre = new Genre(name);
        return genreDao.insert(genre);
    }

    @Override
    public Genre update(Genre genre, String name) {
        Genre updatedGenre = genre.toBuilder().setName(name).build();
        genreDao.update(updatedGenre);
        return updatedGenre;
    }

    @Override
    public void deleteById(long id) {
        genreDao.deleteById(id);
    }
}
