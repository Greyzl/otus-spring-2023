package ru.otus.factory;

import ru.otus.entity.Answer;
import ru.otus.entity.Question;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.ArrayList;


public class QuestionFactory {

    public Question create(String questionText, List<Answer> answerList){
        Map<Integer, Answer> answerMap = new TreeMap<>();
        Random random = new Random();
        List<Integer> integers = new ArrayList<>();
        for (int position = 0; position < answerList.size(); position++){
            integers.add(position);
        }
        for (Answer answer: answerList){
            int integersIndex = random.nextInt(integers.size());
            int randomPosition = integers.get(integersIndex);
            answerMap.put(randomPosition, answer);
            integers.remove(integersIndex);
        }
        return new Question(questionText, answerMap);
    }
}
