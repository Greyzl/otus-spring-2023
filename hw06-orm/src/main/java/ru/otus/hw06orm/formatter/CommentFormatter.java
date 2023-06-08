package ru.otus.hw06orm.formatter;

import ru.otus.hw06orm.persistance.entity.Comment;

import java.util.List;

public interface CommentFormatter {

    String format(List<Comment> comments);
}
