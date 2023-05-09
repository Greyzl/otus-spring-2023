package ru.otus.formatter;

import ru.otus.entity.Answer;
import ru.otus.entity.Question;

import java.util.List;

public interface UserAnswerTextFormatter {

    String format(Question question, List<Answer> answerList);
}
