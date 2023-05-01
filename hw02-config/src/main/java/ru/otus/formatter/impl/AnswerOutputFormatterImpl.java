package ru.otus.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.entity.Answer;
import ru.otus.formatter.AnswerOutputFormatter;

@Component
public class AnswerOutputFormatterImpl implements AnswerOutputFormatter {

    public String format(Answer answer){
        return answer.getText();
    }
}
