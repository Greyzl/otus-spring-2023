package ru.otus;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.service.QuestionService;


public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/context.xml");
        QuestionService questionService = ctx.getBean(QuestionService.class);
        questionService.showQuestions();
    }
}