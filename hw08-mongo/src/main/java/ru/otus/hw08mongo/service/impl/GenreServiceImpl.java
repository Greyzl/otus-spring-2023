package ru.otus.hw08mongo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw08mongo.persistance.entity.Genre;
import ru.otus.hw08mongo.exception.GenreAlreadyExistsException;
import ru.otus.hw08mongo.exception.GenreNotFoundException;
import ru.otus.hw08mongo.persistance.repository.GenreRepository;
import ru.otus.hw08mongo.service.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Genre get(String id) throws GenreNotFoundException {
        return genreRepository.findById(id).orElseThrow(GenreNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Genre getByName(String name) throws GenreNotFoundException {
        return genreRepository.findByName(name).orElseThrow(GenreNotFoundException::new);
    }

    @Transactional
    @Override
    public Genre getOrCreate(String name) {
        try {
            return getByName(name);
        } catch (GenreNotFoundException e) {
            return add(name);
        }
    }

    @Transactional
    @Override
    public Genre add(String name) throws GenreAlreadyExistsException {
        var isExists = genreRepository.existsByName(name);
        if (isExists) {
            throw new GenreAlreadyExistsException();
        }
        Genre genre = new Genre(name);
        return genreRepository.save(genre);
    }

    @Transactional
    @Override
    public Genre update(String id, String name) throws GenreNotFoundException {
        var genre = genreRepository.findById(id).orElseThrow(GenreNotFoundException::new);
        genre.setName(name);
        genreRepository.save(genre);
        return genre;
    }

    @Transactional
    @Override
    public void delete(String id) {
        var genre = genreRepository.findById(id).orElseThrow(GenreNotFoundException::new);
        genreRepository.delete(genre);
    }
}
