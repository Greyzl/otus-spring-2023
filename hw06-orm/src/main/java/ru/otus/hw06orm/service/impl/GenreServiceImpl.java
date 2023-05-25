package ru.otus.hw06orm.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw06orm.entity.Genre;
import ru.otus.hw06orm.persistance.repository.GenreRepository;
import ru.otus.hw06orm.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.getAll();
    }

    @Override
    public Optional<Genre> get(long id) {
        return genreRepository.getById(id);
    }

    @Override
    public Optional<Genre> getByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public Genre getOrCreate(String name) {
        return getByName(name).orElse(add(name));
    }

    @Override
    public Genre add(String name) {
        Genre genre = new Genre(name);
        return genreRepository.save(genre);
    }

    @Override
    public Genre update(Genre genre, String name) {
        Genre updatedGenre = genre.toBuilder().setName(name).build();
        genreRepository.save(updatedGenre);
        return updatedGenre;
    }

    @Override
    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }
}
