package ru.otus.hw06orm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06orm.persistance.entity.Genre;
import ru.otus.hw06orm.exception.GenreAlreadyExistsException;
import ru.otus.hw06orm.exception.GenreNotFoundException;
import ru.otus.hw06orm.persistance.repository.GenreRepository;
import ru.otus.hw06orm.service.GenreService;

import java.util.List;

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
    public Genre get(long id) throws GenreNotFoundException{
        return genreRepository.getById(id).orElseThrow(GenreNotFoundException::new);
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
        } catch (GenreNotFoundException e){
            return add(name);
        }
    }

    @Transactional
    @Override
    public Genre add(String name) throws GenreAlreadyExistsException {
        var mayBeGenre = genreRepository.findByName(name);
        mayBeGenre.ifPresent(genre -> {
            throw new GenreAlreadyExistsException(genre);
        });
        Genre genre = new Genre(name);
        return genreRepository.save(genre);
    }

    @Transactional
    @Override
    public Genre update(long id, String name) throws GenreNotFoundException {
        var genre = genreRepository.getById(id).orElseThrow(GenreNotFoundException::new);
        genre.setName(name);
        genreRepository.save(genre);
        return genre;
    }

    @Transactional
    @Override
    public void delete(long id) {
        var genre = genreRepository.getById(id).orElseThrow(GenreNotFoundException::new);
        genreRepository.delete(genre);
    }
}
