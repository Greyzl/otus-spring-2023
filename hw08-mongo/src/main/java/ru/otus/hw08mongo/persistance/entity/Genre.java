package ru.otus.hw08mongo.persistance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(value = "genres")
public class Genre {

    @Id
    private String id;

    private String name;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genre genre = (Genre) o;
        return name.equalsIgnoreCase(genre.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
