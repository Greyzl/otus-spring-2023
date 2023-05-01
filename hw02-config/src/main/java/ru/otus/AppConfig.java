package ru.otus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.QueationDao;
import ru.otus.dao.impl.QuestionDaoCSV;
import ru.otus.mapper.QuestionMapperCsv;
import ru.otus.service.InputService;
import ru.otus.service.OutputService;
import ru.otus.service.impl.ConsoleInputService;
import ru.otus.service.impl.ConsoleOutputService;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${test.file_name}")
    private String testFilePath;

    @Bean
    public InputService inputService(){
        return new ConsoleInputService(System.in);
    }

    @Bean
    public OutputService outputService(){
        return new ConsoleOutputService(System.out);
    }

    @Bean
    public QuestionMapperCsv questionMapperCsv(){
        return new QuestionMapperCsv();
    }

    @Bean
    public QueationDao queationDao(){
        return new QuestionDaoCSV(questionMapperCsv(), testFilePath);
    }
}
