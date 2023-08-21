package ru.otus.hw08mongo.persistance.repository;

import ru.otus.hw08mongo.persistance.entity.Book;
import ru.otus.hw08mongo.persistance.entity.Comment;

public interface BookRepositoryCustom {

    void addComment(Book book, Comment comment);

    void deleteComment(Book book, Comment comment);

    void deleteWithComments(Book book);
}
