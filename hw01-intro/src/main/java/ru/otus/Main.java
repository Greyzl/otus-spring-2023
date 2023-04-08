package ru.otus;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.service.ApplicationRunner;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/context.xml");
        ApplicationRunner questionApplication = ctx.getBean(ApplicationRunner.class);
        questionApplication.run();
    }
}