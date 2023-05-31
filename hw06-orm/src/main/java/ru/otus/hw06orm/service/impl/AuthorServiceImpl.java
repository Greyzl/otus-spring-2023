package ru.otus.hw06orm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06orm.entity.Author;
import ru.otus.hw06orm.persistance.repository.AuthorRepository;
import ru.otus.hw06orm.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Transactional(readOnly = true)
    public Optional<Author> get(long id) {
        return authorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Author> findByName(String name) {
        return authorRepository.findByName(name);
    }

    @Transactional
    @Override
    public Author getOrCreate(String name) {
        return findByName(name).orElse(add(name));
    }

    @Transactional
    public Author add(String authorName){
        Author newAuthor = new Author(authorName);
        return authorRepository.save(newAuthor);
    }

    @Transactional
    public Author update(Author author, String authorName){
        author.setName(authorName);
        return authorRepository.save(author);
    }

    @Transactional
    public void delete(Author author){
        authorRepository.delete(author);
    }
}
