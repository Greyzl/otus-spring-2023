package ru.otus.mapper;

import ru.otus.entity.Question;
import ru.otus.entity.csv.QuestionCsv;
import ru.otus.exception.QuestionMapException;

import java.util.ArrayList;

public class QuestionMapperCsv {

    public Question mapQuestion(QuestionCsv questionCsv) {
        String questionText = questionCsv.getText();
        if (questionText.isEmpty()) {
            throw new QuestionMapException("Question text is empty");
        }
        var answerOptions = new ArrayList<String>();
        for (String answerOption: questionCsv.getAnswerOptionsList()) {
            if (answerOption.trim().isEmpty()){
                continue;
            }
            answerOptions.add(answerOption.trim());
        }
        String rightAnswer = questionCsv.getRightAnswer().trim();
        if (!answerOptions.isEmpty() && rightAnswer.isEmpty()){
            throw new QuestionMapException("Right answer is empty");
        }
        return new Question(questionText.trim(),rightAnswer, answerOptions);
    }

}
