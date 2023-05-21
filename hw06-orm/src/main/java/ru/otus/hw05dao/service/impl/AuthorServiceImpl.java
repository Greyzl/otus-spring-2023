package ru.otus.hw05dao.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.persistance.repository.AuthorRepository;
import ru.otus.hw05dao.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    public Optional<Author> get(long id) {
        return authorRepository.getById(id);
    }

    public Optional<Author> findByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public Author getOrCreate(String name) {
        return findByName(name).orElse(add(name));
    }

    public Author add(String authorName){
        Author newAuthor = new Author(authorName);
        return authorRepository.save(newAuthor);
    }

    public Author update(Author author, String authorName){
        Author newAuthor = author.toBuilder().setName(authorName).build();
        return authorRepository.save(newAuthor);
    }

    public void deleteById(long id){
        authorRepository.deleteById(id);
    }
}
