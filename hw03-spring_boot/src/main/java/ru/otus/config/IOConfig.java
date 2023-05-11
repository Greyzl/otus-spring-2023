package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.InputService;
import ru.otus.service.impl.InputStreamService;
import ru.otus.service.impl.OutputStreamService;

@Configuration
public class IOConfig {

    @Bean
    public InputService inputService(){
        return new InputStreamService(System.in);
    }

    @Bean
    public OutputStreamService outputStreamService(){
        return new OutputStreamService(System.out);
    }

}
