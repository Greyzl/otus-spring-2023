package ru.otus.service.impl;

import ru.otus.service.OutputService;

import java.io.PrintStream;

public class ConsoleOutputService implements OutputService {

    private final PrintStream printStream;

    public ConsoleOutputService(PrintStream printStream){
        this.printStream = printStream;
    }

    @Override
    public void output(String outputObject) {
        printStream.println(outputObject);
    }
}
