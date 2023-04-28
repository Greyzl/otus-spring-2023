package ru.otus.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.formatter.AnswerOutputFormatter;
import ru.otus.formatter.QuestionOutputFormatter;

@Component
public class ConsoleQuestionOutputFormatter implements QuestionOutputFormatter {

    private final AnswerOutputFormatter answerOutputFormatter;

    public ConsoleQuestionOutputFormatter(AnswerOutputFormatter answerOutputFormatter){
        this.answerOutputFormatter = answerOutputFormatter;
    }

    @Override
    public String format(Question question) {
        StringBuilder builder = new StringBuilder(question.getText());
        if (!question.getAnswerOptions().isEmpty()) {
            builder.append("\n");
            for (Answer answerOption: question.getAnswerOptions()){
                String formattedAnswer = answerOutputFormatter.format(answerOption);
                builder.append(formattedAnswer).append(" ");
            }
        }
        return builder.toString();
    }
}
