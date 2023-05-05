package ru.otus.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.entity.PositionedAnswerOption;
import ru.otus.entity.PositionedAnswerOptions;
import ru.otus.formatter.AnswerOutputFormatter;
import ru.otus.formatter.GetUserAnswerTextFormatter;

@Component
public class GetUserAnswerTextFormatterImpl implements GetUserAnswerTextFormatter {

    private final AnswerOutputFormatter answerOutputFormatter;

    public GetUserAnswerTextFormatterImpl(AnswerOutputFormatter answerOutputFormatter){
        this.answerOutputFormatter = answerOutputFormatter;
    }

    public String format(Question question, PositionedAnswerOptions positionedAnswerOptions){
        StringBuilder builder = new StringBuilder(question.getText());
        builder.append("\n");
        for (PositionedAnswerOption positionedAnswerOption : positionedAnswerOptions.getUserAnswerOptions()){
            Answer answer = positionedAnswerOption.getAnswer();
            int position = positionedAnswerOption.getPosition();
            String answerFormatted = answerOutputFormatter.format(answer);
            String answerOptionFormatted = String.format("%d. %s", position, answerFormatted);
            builder.append(answerOptionFormatted).append(" ");
        }
        return builder.toString();
    }
}
