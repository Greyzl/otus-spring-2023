package ru.otus.service.impl;

import ru.otus.service.InputService;

import java.io.InputStream;
import java.util.Scanner;

public class ConsoleInputService implements InputService {

    private final Scanner scanner;

    public ConsoleInputService(InputStream inputStream){
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String read() {
        return scanner.nextLine().trim();
    }
}
