package ru.otus.entity.csv;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.List;

public class QuestionCsv {
    @CsvBindByName(column = "text", required = true)
    private String text;


    @CsvBindAndJoinByName(column = "answer_option_[0-9]+", elementType = String.class,
            mapType = ArrayListValuedHashMap.class)
    private MultiValuedMap<String, String> answerOptions;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswerOptions(MultiValuedMap<String, String> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public List<String> getAnswerOptionsList(){

        return answerOptions.values().stream().toList();
    }

}
