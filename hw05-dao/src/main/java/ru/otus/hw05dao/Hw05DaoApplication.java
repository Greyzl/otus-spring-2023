package ru.otus.hw05dao;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hw05DaoApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(Hw05DaoApplication.class, args);
		Console.main(args);
	}

}
