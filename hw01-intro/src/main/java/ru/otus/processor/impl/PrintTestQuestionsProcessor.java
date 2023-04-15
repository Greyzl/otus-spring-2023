package ru.otus.processor.impl;

import ru.otus.entity.Question;
import ru.otus.formatter.QuestionOutputFormatter;
import ru.otus.processor.TestProcessor;
import ru.otus.service.OutputService;
import ru.otus.service.QuestionService;
import ru.otus.service.UserService;

import java.util.List;

public class PrintTestQuestionsProcessor implements TestProcessor {

    private final QuestionService questionService;

    private final QuestionOutputFormatter outputFormatter;

    private final UserService userService;

    private final OutputService outputService;

    public PrintTestQuestionsProcessor(QuestionService questionService,
                                       QuestionOutputFormatter outputFormatter,
                                       UserService userService,
                                       OutputService outputService ) {
        this.questionService = questionService;
        this.outputFormatter = outputFormatter;
        this.userService = userService;
        this.outputService = outputService;
    }

    @Override
    public void process() {
        userService.getUser();
        List<Question> questions = questionService.getQuestions();
        for (Question question: questions){
            String formattedQuestion = outputFormatter.format(question);
            outputService.output(formattedQuestion);
        }
    }
}
