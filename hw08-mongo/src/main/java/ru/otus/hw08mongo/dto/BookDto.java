package ru.otus.hw08mongo.dto;

public class BookDto {
    private String id;

    private String title;

    private String authorName;

    private String genreName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
