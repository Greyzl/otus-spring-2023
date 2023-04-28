package ru.otus.mapper;

import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.entity.csv.QuestionCsv;
import ru.otus.exception.QuestionMapException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionMapperCsv {

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
        List<Answer> answerOptions = mapAnswerOptions(answerOptionTexts);
        Optional<Answer> mayBeRightAnswer = findRightAnswer(rightAnswerTextTrimmed,answerOptions);
        if (mayBeRightAnswer.isEmpty()){
            throw new QuestionMapException("There is no right answer in answer options");
        }
        return new Question(questionText.trim(), mayBeRightAnswer.get(), answerOptions);
    }

    private List<Answer> mapAnswerOptions(List<String> answerOptionTexts){
        var answerOptions = new ArrayList<Answer>();
        int position = 0;
        for (String answerOptionText: answerOptionTexts) {
            String answerOptionTextTrimmed = answerOptionText.trim();
            position++;
            if (answerOptionTextTrimmed.isEmpty()){
                continue;
            }
            Answer answerOption = new Answer(position, answerOptionTextTrimmed);
            answerOptions.add(answerOption);
        }
        return answerOptions;
    }

    private Optional<Answer> findRightAnswer(String rightAnswerText, List<Answer> answerOptions){
        return answerOptions.stream().filter(answer -> answer.getText().equals(rightAnswerText)).findFirst();
    }

}
