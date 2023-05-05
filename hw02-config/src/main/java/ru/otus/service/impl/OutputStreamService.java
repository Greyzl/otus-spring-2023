package ru.otus.service.impl;

import ru.otus.service.OutputService;

import java.io.PrintStream;

public class OutputStreamService implements OutputService {

    private final PrintStream printStream;

    public OutputStreamService(PrintStream printStream){
        this.printStream = printStream;
    }

    @Override
    public void output(String outputObject) {
        printStream.println(outputObject);
    }
}
