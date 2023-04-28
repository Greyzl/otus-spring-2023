package ru.otus.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.entity.TestResult;
import ru.otus.formatter.TestResultOutputFormatter;

@Component
public class ConsoleTestResultOutputFormatter implements TestResultOutputFormatter {

    @Override
    public String format(TestResult testResult) {
        return String.format("%s, your result is %d/%d",
                testResult.getUserName(),
                testResult.getRightAnswers(),
                testResult.getQuestionCount());
    }
}
