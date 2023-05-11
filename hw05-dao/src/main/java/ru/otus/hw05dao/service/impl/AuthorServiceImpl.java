package ru.otus.hw05dao.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw05dao.dao.AuthorDao;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao){
        this.authorDao = authorDao;
    }

    public List<Author> getAll() {
        return authorDao.getAll();
    }

    public Optional<Author> get(long id) {
        return authorDao.getById(id);
    }

    public Optional<Author> findByName(String name) {
        return authorDao.findByName(name);
    }

    @Override
    public Author getOrCreate(String name) {
        return findByName(name).orElse(add(name));
    }

    public Author add(String authorName){
        Author newAuthor = new Author(authorName);
        return authorDao.insert(newAuthor);
    }

    public Author update(Author author, String authorName){
        Author newAuthor = author.toBuilder().setName(authorName).build();
        authorDao.update(newAuthor);
        return newAuthor;
    }

    public void deleteById(long id){
        authorDao.deleteById(id);
    }
}
