package ru.otus.hw08mongo.formatter;

import ru.otus.hw08mongo.persistance.entity.Comment;

import java.util.List;

public interface CommentFormatter {

    String format(List<Comment> comments);
}
