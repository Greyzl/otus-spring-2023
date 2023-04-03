package ru.otus.service.imp;

import ru.otus.service.OutputService;

import java.io.PrintStream;
import java.util.List;

public class OutputServiceImp implements OutputService {

    private final PrintStream printStream;

    public OutputServiceImp(PrintStream printStream){
        this.printStream = printStream;
    }

    @Override
    public <T> void output(List<T> objects) {
        for (T object: objects){
            printStream.println(object);
        }
        System.out.println();
    }
}
