package ru.otus.processor.impl;

import ru.otus.entity.Question;
import ru.otus.formatter.QuestionOutputFormatter;
import ru.otus.processor.TestProcessor;
import ru.otus.service.OutputService;
import ru.otus.service.QuestionService;
import ru.otus.service.UserQuestionService;

import java.util.List;

public class PrintTestQuestionsProcessor implements TestProcessor {

    private final QuestionService questionService;

    private final UserQuestionService userQuestionService;

    private final QuestionOutputFormatter outputFormatter;

    private final OutputService outputService;

    public PrintTestQuestionsProcessor(QuestionService questionService,
                                       UserQuestionService userQuestionService,
                                       QuestionOutputFormatter outputFormatter,
                                       OutputService outputService ) {
        this.questionService = questionService;
        this.userQuestionService = userQuestionService;
        this.outputFormatter = outputFormatter;
        this.outputService = outputService;
    }

    @Override
    public void process() {
        Question userNameQuestion = userQuestionService.getUserNameQuestion();
        String formattedUserNameQuestion = outputFormatter.format(userNameQuestion);
        outputService.output(formattedUserNameQuestion);
        List<Question> questions = questionService.getQuestions();
        for (Question question: questions){
            String formattedQuestion = outputFormatter.format(question);
            outputService.output(formattedQuestion);
        }
    }
}
