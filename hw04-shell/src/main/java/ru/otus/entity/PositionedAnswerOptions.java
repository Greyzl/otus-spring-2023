package ru.otus.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class PositionedAnswerOptions {
    private final Set<PositionedAnswerOption> positionedAnswerOptions = new TreeSet<>();

    public PositionedAnswerOptions(List<Answer> answerOption){
        Random random = new Random();
        List<Integer> integers = new ArrayList<>();
        for (int position = 0; position < answerOption.size(); position++){
            integers.add(position + 1);
        }
        for (Answer answer: answerOption){
            int integersIndex = random.nextInt(integers.size());
            int randomPosition = integers.get(integersIndex);
            PositionedAnswerOption positionedAnswerOption = new PositionedAnswerOption(randomPosition, answer);
            positionedAnswerOptions.add(positionedAnswerOption);
            integers.remove(integersIndex);
        }
    }

    public Optional<PositionedAnswerOption> get(int position){
        return positionedAnswerOptions.stream().filter(positionedAnswerOption
                -> positionedAnswerOption.getPosition() == position).findFirst();
    }

    public Set<PositionedAnswerOption> getUserAnswerOptions(){
        return positionedAnswerOptions;
    }
}
