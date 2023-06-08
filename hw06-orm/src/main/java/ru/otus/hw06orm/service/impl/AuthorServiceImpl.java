package ru.otus.hw06orm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06orm.persistance.entity.Author;
import ru.otus.hw06orm.exception.AuthorAlreadyExistsException;
import ru.otus.hw06orm.exception.AuthorNotFoundException;
import ru.otus.hw06orm.persistance.repository.AuthorRepository;
import ru.otus.hw06orm.service.AuthorService;

import java.util.List;

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
    public Author get(long id) {
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
        }catch (AuthorNotFoundException e){
            return add(name);
        }
    }

    @Transactional
    public Author add(String authorName) throws AuthorAlreadyExistsException{
        authorRepository.findByName(authorName).ifPresent((author) -> {
            throw new AuthorAlreadyExistsException(author);
        });
        Author newAuthor = new Author(authorName);
        return authorRepository.save(newAuthor);
    }

    @Transactional
    public Author update(long id, String authorName) throws AuthorNotFoundException{
        var author = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
        author.setName(authorName);
        return authorRepository.save(author);
    }

    @Transactional
    public void delete(long id){
        var author = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
        authorRepository.delete(author);
    }
}
