package ru.otus.service;

import org.springframework.stereotype.Component;
import ru.otus.entity.Question;
import ru.otus.entity.TestResult;
import ru.otus.entity.User;
import ru.otus.formatter.TestResultOutputFormatter;

import java.util.List;

@Component
public class ApplicationRunner {

    private final OutputService outputService;

    private final QuestionService questionService;

    private final TestService testService;

    private final TestResultOutputFormatter testResultOutputFormatter;

    private final UserService userService;

    public ApplicationRunner(OutputService outputService,
                             QuestionService questionService,
                             TestService testService,
                             TestResultOutputFormatter testResultOutputFormatter,
                             UserService userService){
        this.outputService = outputService;
        this.questionService = questionService;
        this.testService = testService;
        this.testResultOutputFormatter = testResultOutputFormatter;
        this.userService = userService;
    }

    public void run(){
        User user = userService.getUser();
        List<Question> questions = questionService.getQuestions();
        TestResult testResult = testService.test(user, questions);
        String formattedResult = testResultOutputFormatter.format(testResult);
        outputService.output(formattedResult);
    }
}
