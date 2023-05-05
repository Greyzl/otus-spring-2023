package ru.otus.entity;

public class PositionedAnswerOption implements Comparable<PositionedAnswerOption> {
    private final int position;

    private final Answer answer;

    public PositionedAnswerOption(int position, Answer answer){
        this.position = position;
        this.answer = answer;
    }

    public int getPosition() {
        return position;
    }

    public Answer getAnswer() {
        return answer;
    }

    @Override
    public int compareTo(PositionedAnswerOption o) {
        return Integer.compare(this.position, o.position);
    }
}
