package ru.otus.service.impl;

import org.springframework.stereotype.Component;
import ru.otus.service.InputService;

import java.util.Scanner;

@Component
public class ConsoleInputService implements InputService {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String read() {
        return scanner.nextLine().trim();
    }
}
