package ru.otus.mapper;

import ru.otus.entity.Question;
import ru.otus.entity.csv.QuestionCsv;

public class QuestionMapperCsv {

    public Question mapQuestion(QuestionCsv questionCsv) {
        Question question = new Question();
        question.setText(questionCsv.getText().trim());
        question.setPrompt(questionCsv.getQuestionVariants().get(0).trim());
        return question;
    }
}
