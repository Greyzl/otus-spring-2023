package ru.otus.hw08mongo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw08mongo.persistance.entity.Author;
import ru.otus.hw08mongo.exception.AuthorAlreadyExistsException;
import ru.otus.hw08mongo.exception.AuthorNotFoundException;
import ru.otus.hw08mongo.persistance.repository.AuthorRepository;
import ru.otus.hw08mongo.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Author get(String id) {
        return authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Author findByName(String name) {
        return authorRepository.findByName(name).orElseThrow(AuthorNotFoundException::new);
    }

    @Transactional
    @Override
    public Author getOrCreate(String name) {
        try {
            return findByName(name);
        } catch (AuthorNotFoundException e) {
            return add(name);
        }
    }

    @Transactional
    public Author add(String authorName) throws AuthorAlreadyExistsException {
        var isExists = authorRepository.existsByName(authorName);
        if (isExists) {
            throw new AuthorAlreadyExistsException();
        }
        Author newAuthor = new Author(authorName);
        return authorRepository.save(newAuthor);
    }

    @Transactional
    public Author update(String id, String authorName) throws AuthorNotFoundException {
        var author = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
        author.setName(authorName);
        return authorRepository.save(author);
    }

    @Transactional
    public void delete(String id) {
        var author = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
        authorRepository.delete(author);
    }
}
