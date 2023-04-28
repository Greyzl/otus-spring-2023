package ru.otus.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.entity.Answer;
import ru.otus.formatter.AnswerOutputFormatter;

@Component
public class ConsoleAnswerOutputFormatter implements AnswerOutputFormatter {

    @Override
    public String format(Answer answer) {
        return String.format("%d. %s", answer.getPosition(), answer.getText());
    }
}
