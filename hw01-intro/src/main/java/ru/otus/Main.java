package ru.otus;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.entity.Question;
import ru.otus.service.QuestionService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/context.xml");
        QuestionService questionService = ctx.getBean(QuestionService.class);
        List<Question> questions = questionService.getQuestions();
        for (Question question: questions){
            System.out.println(question);
        }
    }
}