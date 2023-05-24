package ru.otus.untils;


public class AnswerOptionUtils {
    public static int getPosition(int arrayIndex){
        return arrayIndex + 1;
    }

    public static int getArrayIndex(int position){
        return position - 1;
    }
}
