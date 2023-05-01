package ru.otus.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.formatter.AnswerOutputFormatter;
import ru.otus.formatter.QuestionOutputFormatter;

import java.util.Map;

@Component
public class ConsoleQuestionOutputFormatter implements QuestionOutputFormatter {

    private AnswerOutputFormatter answerOutputFormatter;

    public ConsoleQuestionOutputFormatter(AnswerOutputFormatter answerOutputFormatter){
        this.answerOutputFormatter = answerOutputFormatter;
    }

    @Override
    public String format(Question question) {
        StringBuilder builder = new StringBuilder(question.getText());
        if (!question.getAnswerOptions().isEmpty()) {
            builder.append("\n");
            for (Map.Entry<Integer, Answer> answerOption: question.getAnswerOptions().entrySet()){
                String formattedAnswer = entrySetAnswerOptionFormat(answerOption);
                builder.append(formattedAnswer).append(" ");
            }
        }
        return builder.toString();
    }

    private String entrySetAnswerOptionFormat(Map.Entry<Integer, Answer> answerOption){
        Answer answer = answerOption.getValue();
        String answerFormatted = answerOutputFormatter.format(answer);
        return String.format("%d. %s", answerOption.getKey(), answerFormatted);
    }
}
