package ru.otus.hw06orm;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hw06DaoApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(Hw06DaoApplication.class, args);
		Console.main(args);
	}

}
