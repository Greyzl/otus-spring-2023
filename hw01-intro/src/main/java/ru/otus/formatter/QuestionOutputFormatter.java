package ru.otus.formatter;

import ru.otus.entity.Question;

public interface QuestionOutputFormatter {

    String format(Question question);
}
