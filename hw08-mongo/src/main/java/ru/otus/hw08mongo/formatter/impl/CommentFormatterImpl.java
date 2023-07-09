package ru.otus.hw08mongo.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.hw08mongo.persistance.entity.Comment;
import ru.otus.hw08mongo.formatter.CommentFormatter;

import java.util.List;

@Component
public class CommentFormatterImpl implements CommentFormatter {

    @Override
    public String format(List<Comment> comments) {
        var builder = new StringBuilder();
        int index = 0;
        for (Comment comment: comments) {
            ++ index;
            builder.append(String.format("%d. id: %s, comment: %s", index, comment.getId(),
                    comment.getText())).append("\n");
        }
        return builder.toString();
    }
}
