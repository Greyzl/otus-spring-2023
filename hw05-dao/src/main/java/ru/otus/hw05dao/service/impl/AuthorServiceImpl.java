package ru.otus.hw05dao.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw05dao.dao.AuthorDao;
import ru.otus.hw05dao.entity.Author;
import ru.otus.hw05dao.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao){
        this.authorDao = authorDao;
    }

    public List<Author> getAll() {
        return authorDao.getAll();
    }

    public Author get(long id) {
        return authorDao.getById(id);
    }
}
