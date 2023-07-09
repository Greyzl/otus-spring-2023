package ru.otus.hw08mongo.persistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08mongo.persistance.entity.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
