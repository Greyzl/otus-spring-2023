package ru.otus.service.impl;

import ru.otus.service.InputService;

import java.io.InputStream;
import java.util.Scanner;

public class InputStreamService implements InputService {

    private final Scanner scanner;

    public InputStreamService(InputStream inputStream){
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String read() {
        return scanner.nextLine().trim();
    }
}
