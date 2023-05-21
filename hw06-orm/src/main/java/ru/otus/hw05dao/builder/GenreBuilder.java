package ru.otus.hw05dao.builder;

import ru.otus.hw05dao.entity.Genre;

public class GenreBuilder {
    private long id;

    private String name;


    public GenreBuilder(long id, String name){
        this.id = id;
        this.name = name;
    }

    public GenreBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public GenreBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Genre build(){
        return new Genre(id, name);
    }
}
