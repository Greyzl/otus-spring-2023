package ru.otus.service;

import ru.otus.processor.TestProcessor;

public class ApplicationRunner {

    private final TestProcessor processor;

    public ApplicationRunner (TestProcessor processor){
        this.processor = processor;
    }

    public void run(){
        processor.process();
    }
}
