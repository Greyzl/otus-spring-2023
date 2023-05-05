package ru.otus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.QueationDao;
import ru.otus.dao.impl.QuestionCSVDao;
import ru.otus.mapper.QuestionMapperCsv;
import ru.otus.service.InputService;
import ru.otus.service.impl.InputStreamService;
import ru.otus.service.impl.OutputStreamService;

@Configuration
@ComponentScan(basePackages = "ru.otus")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${test.file_name}")
    private String testFilePath;

    @Bean
    public InputService inputService(){
        return new InputStreamService(System.in);
    }

    @Bean
    public OutputStreamService outputStreamService(){
        return new OutputStreamService(System.out);
    }

    @Bean
    public QueationDao queationDao(QuestionMapperCsv questionMapperCsv){
        return new QuestionCSVDao(questionMapperCsv, testFilePath);
    }
}
