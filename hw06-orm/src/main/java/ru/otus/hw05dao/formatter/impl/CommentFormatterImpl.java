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
        int index = 0;
        for (Comment comment: comments){
            ++ index;
            builder.append(String.format("%d. %s", index, comment.getText())).append("\n");
        }
        return builder.toString();
    }
}
