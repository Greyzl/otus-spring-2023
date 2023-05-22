package ru.otus.hw05dao.formatter;

import ru.otus.hw05dao.entity.Comment;

import java.util.List;

public interface CommentFormatter {

    String format(List<Comment> comments);
}
