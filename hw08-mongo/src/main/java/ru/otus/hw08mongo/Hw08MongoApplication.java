package ru.otus.hw08mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Hw08MongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hw08MongoApplication.class, args);
	}

}
