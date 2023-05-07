package ru.otus.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.entity.Question;
import ru.otus.entity.TestResult;
import ru.otus.entity.User;
import ru.otus.formatter.TestResultOutputFormatter;
import ru.otus.service.OutputService;
import ru.otus.service.QuestionService;
import ru.otus.service.TestService;
import ru.otus.service.UserService;

import java.util.List;

@ShellComponent
public class TestController {

    private final OutputService outputService;

    private final QuestionService questionService;

    private final TestService testService;

    private final TestResultOutputFormatter testResultOutputFormatter;

    private final UserService userService;

    public TestController(OutputService outputService,
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

    @ShellMethod
    public String run(){
        User user = userService.getUser();
        List<Question> questions = questionService.getQuestions();
        TestResult testResult = testService.test(user, questions);
        String formattedResult = testResultOutputFormatter.format(testResult);
        outputService.output(formattedResult);
        return "Good bye";
    }
}
