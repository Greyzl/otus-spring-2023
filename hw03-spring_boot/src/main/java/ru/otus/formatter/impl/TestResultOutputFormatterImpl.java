package ru.otus.formatter.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.config.AppProps;
import ru.otus.entity.TestResult;
import ru.otus.formatter.TestResultOutputFormatter;

@Component
public class TestResultOutputFormatterImpl implements TestResultOutputFormatter {

    private final MessageSource messageSource;

    private final AppProps appProps;

    public TestResultOutputFormatterImpl(MessageSource messageSource,
                                         AppProps appProps){
        this.messageSource = messageSource;
        this.appProps = appProps;
    }

    @Override
    public String format(TestResult testResult) {
        return messageSource.getMessage(
                "test.messages.result",
                new Object[]{testResult.getUserName(),testResult.getRightAnswers(),testResult.getQuestionCount()},
                appProps.getLocale());
    }
}
