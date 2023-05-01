package ru.otus.mapper;

import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.entity.csv.QuestionCsv;
import ru.otus.exception.QuestionMapException;
import ru.otus.factory.QuestionFactory;

import java.util.ArrayList;
import java.util.List;

public class QuestionMapperCsv {
    private final QuestionFactory questionFactory = new QuestionFactory();

    public Question mapQuestion(QuestionCsv questionCsv) {
        String questionText = questionCsv.getText();
        if (questionText.isEmpty()) {
            throw new QuestionMapException("Question text is empty");
        }
        String rightAnswerTextTrimmed = questionCsv.getRightAnswer().trim();
        if (rightAnswerTextTrimmed.isEmpty()){
            throw new QuestionMapException("Right answer is empty");
        }
        List<String> answerOptionTexts = questionCsv.getAnswerOptionsList();
        List<Answer> answerOptions = mapAnswerOptions(rightAnswerTextTrimmed, answerOptionTexts);
        return questionFactory.create(questionText.trim(), answerOptions);
    }

    private List<Answer> mapAnswerOptions(String rightAnswerText, List<String> answerOptionTexts){
        var answerOptions = new ArrayList<Answer>();
        Answer rightAnswer = new Answer(rightAnswerText, true);
        answerOptions.add(rightAnswer);
        for (String answerOptionText: answerOptionTexts) {
            String answerOptionTextTrimmed = answerOptionText.trim();
            if (answerOptionTextTrimmed.isEmpty()){
                continue;
            }
            Answer answerOption = new Answer(answerOptionTextTrimmed, false);
            answerOptions.add(answerOption);
        }
        return answerOptions;
    }
}
