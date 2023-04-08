package ru.otus.formatter.imp;

import ru.otus.entity.Question;
import ru.otus.formatter.QuestionOutputFormatter;

public class ConsoleQuestionOutputFormatter implements QuestionOutputFormatter {

    public ConsoleQuestionOutputFormatter(){
    }

    @Override
    public String format(Question question) {
        StringBuilder builder = new StringBuilder(question.getText());
        if (!question.getAnswerOptions().isEmpty()) {
            builder.append("\n");
            int iteration = 1;
            for (String answerOption: question.getAnswerOptions()){
                builder.append(iteration).append(". ").append(answerOption).append(" ");
                iteration++;
            }
        }
        return builder.toString();
    }
}
