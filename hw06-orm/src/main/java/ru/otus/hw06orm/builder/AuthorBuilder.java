package ru.otus.hw06orm.builder;

import ru.otus.hw06orm.entity.Author;

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
