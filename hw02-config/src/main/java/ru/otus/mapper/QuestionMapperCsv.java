package ru.otus.mapper;

import org.springframework.stereotype.Component;
import ru.otus.entity.Answer;
import ru.otus.entity.Question;
import ru.otus.dto.QuestionCsvDto;
import ru.otus.exception.QuestionMapException;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionMapperCsv {

    public Question mapQuestion(QuestionCsvDto questionCsvDto) {
        String questionText = questionCsvDto.getText();
        if (questionText.isEmpty()) {
            throw new QuestionMapException("Question text is empty");
        }
        String rightAnswerTextTrimmed = questionCsvDto.getRightAnswer().trim();
        if (rightAnswerTextTrimmed.isEmpty()){
            throw new QuestionMapException("Right answer is empty");
        }
        List<String> answerOptionTexts = questionCsvDto.getAnswerOptionsList();
        List<Answer> answerOptions = mapAnswerOptions(rightAnswerTextTrimmed, answerOptionTexts);
        return new Question(questionText.trim(), answerOptions);
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
