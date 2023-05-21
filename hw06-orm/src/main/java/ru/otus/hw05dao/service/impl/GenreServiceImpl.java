package ru.otus.hw05dao.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw05dao.entity.Genre;
import ru.otus.hw05dao.persistance.repository.GenreRepository;
import ru.otus.hw05dao.service.GenreService;

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
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }
}
