package ru.otus.formatter;

import ru.otus.entity.PositionedAnswerOptions;
import ru.otus.entity.Question;

public interface GetUserAnswerTextFormatter {

    String format(Question question, PositionedAnswerOptions positionedAnswerOptions);
}
