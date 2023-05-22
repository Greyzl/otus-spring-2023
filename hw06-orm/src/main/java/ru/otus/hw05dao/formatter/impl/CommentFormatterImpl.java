package ru.otus.hw05dao.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw05dao.entity.Comment;
import ru.otus.hw05dao.formatter.CommentFormatter;

import java.util.List;

@Component
public class CommentFormatterImpl implements CommentFormatter {

    @Override
    public String format(List<Comment> comments) {
        var builder = new StringBuilder();
        for (Comment comment: comments){
            builder.append(format(comment)).append("\n");
        }
        return builder.toString();
    }

    private String format(Comment comment){
        return String.format("%d. %s", comment.getId(), comment.getText());
    }
}
