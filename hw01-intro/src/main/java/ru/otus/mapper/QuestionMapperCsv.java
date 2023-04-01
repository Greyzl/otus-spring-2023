package ru.otus.mapper;

import ru.otus.entity.Question;
import ru.otus.entity.csv.QuestionCsv;
import ru.otus.entity.enums.QuestionType;

import java.util.Optional;

public class QuestionMapperCsv {
    public Question mapQuestion(QuestionCsv questionCsv) {
        Optional<QuestionType> mayBeType = QuestionType.getByType(questionCsv.getType().trim());
        Question question;
        if (mayBeType.isPresent()){
            //Можно добавить фабрику
            question = new Question();
            question.setText(questionCsv.getText().trim());
            question.setType(mayBeType.get());
            //Так как сейчас всего один тип вопросов, замаплю прямо тут
            question.setPrompt(questionCsv.getQuestionVariants().get(0).trim());
        } else {
            throw new IllegalArgumentException("Incorrect question type");
        }
        return question;
    }
}
