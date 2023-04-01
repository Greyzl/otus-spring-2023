package ru.otus.entity.csv;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.util.List;

public class QuestionCsv {
    @CsvBindByName(column = "text")
    private String text;

    @CsvBindByName(column = "type")
    private String type;

    @CsvBindAndJoinByName(column = "parameter[0-9]+", elementType = String.class,
            mapType = HashSetValuedHashMap.class, required = true)
    private MultiValuedMap<String, String> args;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MultiValuedMap<String, String> getArgs() {
        return args;
    }

    public void setArgs(MultiValuedMap<String, String> args) {
        this.args = args;
    }

    public List<String> getQuestionVariants(){
        return args.values().stream().toList();
    }

}
