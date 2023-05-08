package ru.otus.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.formatter.AnswerOutputFormatter;
import ru.otus.formatter.UserAnswerTextFormatter;
import ru.otus.untils.AnswerOptionUtils;

import java.util.List;

@Component
public class UserAnswerTextFormatterImpl implements UserAnswerTextFormatter {

    private final AnswerOutputFormatter answerOutputFormatter;

    public UserAnswerTextFormatterImpl(AnswerOutputFormatter answerOutputFormatter){
        this.answerOutputFormatter = answerOutputFormatter;
    }

    public String format(Question question, List<Answer> answerList){
        StringBuilder builder = new StringBuilder(question.getText());
        builder.append("\n");
        int arrayIndex = 0;
        for (Answer answer : answerList){
            String answerFormatted = answerOutputFormatter.format(answer);
            int position = AnswerOptionUtils.getPosition(arrayIndex);
            String answerOptionFormatted = String.format("%d. %s", position, answerFormatted);
            builder.append(answerOptionFormatted).append(" ");
            arrayIndex++;
        }
        return builder.toString();
    }
}
