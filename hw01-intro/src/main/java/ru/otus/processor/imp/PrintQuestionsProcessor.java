package ru.otus.processor.imp;

import ru.otus.entity.Question;
import ru.otus.formatter.QuestionOutputFormatter;
import ru.otus.processor.Processor;
import ru.otus.service.OutputService;
import ru.otus.service.QuestionService;

import java.util.List;

public class PrintQuestionsProcessor implements Processor {

    private final QuestionService questionService;


    private final QuestionOutputFormatter outputFormatter;

    private final OutputService outputService;

    public PrintQuestionsProcessor(QuestionService questionService,
                                   QuestionOutputFormatter outputFormatter,
                                   OutputService outputService ) {
        this.questionService = questionService;
        this.outputFormatter = outputFormatter;
        this.outputService = outputService;
    }

    @Override
    public void process() {
        List<Question> questions = questionService.getQuestions();
        for (Question question: questions){
            String formattedQuestion = outputFormatter.format(question);
            outputService.output(formattedQuestion);
        }
    }
}
