package ru.otus.hw06orm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll() {
        return genreRepository.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> get(long id) {
        return genreRepository.getById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getByName(String name) {
        return genreRepository.findByName(name);
    }

    @Transactional
    @Override
    public Genre getOrCreate(String name) {
        return getByName(name).orElse(add(name));
    }

    @Transactional
    @Override
    public Genre add(String name) {
        Genre genre = new Genre(name);
        return genreRepository.save(genre);
    }

    @Transactional
    @Override
    public Genre update(Genre genre, String name) {
        genre.setName(name);
        genreRepository.save(genre);
        return genre;
    }

    @Transactional
    @Override
    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }
}
