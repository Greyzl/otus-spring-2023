package ru.otus;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.config.AppConfig;
import ru.otus.service.ApplicationRunner;


public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ApplicationRunner questionApplication = ctx.getBean(ApplicationRunner.class);
        questionApplication.run();
    }
}