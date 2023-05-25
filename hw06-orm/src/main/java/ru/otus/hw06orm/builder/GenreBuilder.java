package ru.otus.hw06orm.builder;

import ru.otus.hw06orm.entity.Genre;

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
