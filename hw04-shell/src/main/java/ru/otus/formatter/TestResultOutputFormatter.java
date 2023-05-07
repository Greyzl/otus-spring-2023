package ru.otus.formatter;

import ru.otus.entity.TestResult;

public interface TestResultOutputFormatter {

    String format(TestResult testResult);
}
