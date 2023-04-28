package ru.otus.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.service.OutputService;

import java.io.PrintStream;

@Service
public class ConsoleOutputService implements OutputService {

    private final PrintStream printStream = System.out;

    public ConsoleOutputService(){
    }

    @Override
    public void output(String outputObject) {
        printStream.println(outputObject);
    }
}
