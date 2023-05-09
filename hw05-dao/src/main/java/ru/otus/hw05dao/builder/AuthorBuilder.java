package ru.otus.hw05dao.builder;

import ru.otus.hw05dao.entity.Author;

public class AuthorBuilder {
    private long id;

    private String name;

    public AuthorBuilder(long id, String name){
        this.id = id;
        this.name = name;
    }

    public AuthorBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public AuthorBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Author build(){
        return new Author(this.id, this.name);
    }
}
