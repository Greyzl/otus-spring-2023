package ru.otus.hw08mongo.persistance.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw08mongo.persistance.entity.Book;
import ru.otus.hw08mongo.persistance.entity.Comment;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final CommentRepository commentRepository;

    private final MongoTemplate mongoTemplate;

    public BookRepositoryCustomImpl(MongoTemplate mongoTemplate,
                                    CommentRepository commentRepository) {
        this.mongoTemplate = mongoTemplate;
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(Book book, Comment comment) {
        comment.setBook(book);
        Comment commentWithId = commentRepository.save(comment);
        book.getComments().add(commentWithId);
        mongoTemplate.save(book);
    }

    @Override
    public void deleteComment(Book book, Comment comment) {
        book.getComments().remove(comment);
        commentRepository.delete(comment);
        mongoTemplate.save(book);
    }

    public void deleteWithComments(Book book) {
        var comments = book.getComments();
        commentRepository.deleteAll(comments);
        mongoTemplate.remove(book);
    }
}
