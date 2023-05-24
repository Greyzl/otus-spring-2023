package ru.otus.formatter.impl;

import org.springframework.stereotype.Component;
import ru.otus.entity.TestResult;
import ru.otus.formatter.TestResultOutputFormatter;
import ru.otus.service.LocalizedMessageService;

@Component
public class TestResultOutputFormatterImpl implements TestResultOutputFormatter {

    private final LocalizedMessageService localizedMessageService;

    public TestResultOutputFormatterImpl(LocalizedMessageService localizedMessageService){
        this.localizedMessageService = localizedMessageService;
    }

    @Override
    public String format(TestResult testResult) {
        return localizedMessageService.getMessage(
                "test.messages.result",
                new Object[]{testResult.getUserName(),testResult.getRightAnswers(),testResult.getQuestionCount()});
    }
}
