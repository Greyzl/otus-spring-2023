package ru.otus.hw08mongo.changelog;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw08mongo.persistance.entity.Author;
import ru.otus.hw08mongo.persistance.entity.Book;
import ru.otus.hw08mongo.persistance.entity.Comment;
import ru.otus.hw08mongo.persistance.entity.Genre;

import java.util.List;

@ChangeUnit(id = "Init database", author = "Shilkov S.S.", order = "1")
public class InitDataBaseChange {

    private MongoTemplate mongoTemplate;

    @Execution
    public void changeSet() {
        mongoTemplate.createCollection(Book.class);
        mongoTemplate.createCollection(Author.class);
        mongoTemplate.createCollection(Genre.class);
        mongoTemplate.createCollection(Comment.class);

        Author author1 = new Author("test author 1");
        Author author2 = new Author("test author 2");
        Author author3 = new Author("test author 3");
        Author author4 = new Author("test author 4");
        Author author5 = new Author("test author 5");
        mongoTemplate.save(author1);
        mongoTemplate.save(author2);
        mongoTemplate.save(author3);
        mongoTemplate.save(author4);
        mongoTemplate.save(author5);

        Genre genre1 = new Genre("test genre 1");
        Genre genre2 = new Genre("test genre 2");
        Genre genre3 = new Genre("test genre 3");
        mongoTemplate.save(genre1);
        mongoTemplate.save(genre2);
        mongoTemplate.save(genre3);

        Book book1 = new Book("test title book 1", author1, genre1);
        Book book2 = new Book("test title book 2", author2, genre1);
        Book book3 = new Book("test title book 3", author2, genre2);
        Book book4 = new Book("test title book 4", author3, genre2);
        Book book5 = new Book("test title book 5", author4, genre2);

        mongoTemplate.save(book1);
        mongoTemplate.save(book2);
        mongoTemplate.save(book3);
        mongoTemplate.save(book4);
        mongoTemplate.save(book5);

        Comment comment1 = new Comment("FIRST COMMENT");
        comment1.setBook(book1);
        Comment comment2 = new Comment("SECOND COMMENT");
        comment2.setBook(book1);
        Comment comment3 = new Comment("THIRD COMMENT");
        comment3.setBook(book2);
        mongoTemplate.save(comment1);
        mongoTemplate.save(comment2);
        mongoTemplate.save(comment3);

        List<Comment>  book1Comments = book1.getComments();
        book1Comments.add(comment1);
        book1Comments.add(comment2);
        mongoTemplate.save(book1);
        List<Comment>  book2Comments = book2.getComments();
        book2Comments.add(comment3);

        mongoTemplate.save(book2);
    }

    @RollbackExecution
    public void rollbackExec() {
        mongoTemplate.dropCollection(Book.class);
        mongoTemplate.dropCollection(Author.class);
        mongoTemplate.dropCollection(Genre.class);
        mongoTemplate.dropCollection(Comment.class);
    }
}
