package ru.otus.service;

import ru.otus.processor.Processor;

public class ApplicationRunner {

    private final Processor processor;

    public ApplicationRunner (Processor processor){
        this.processor = processor;
    }

    public void run(){
        processor.process();
    }
}
