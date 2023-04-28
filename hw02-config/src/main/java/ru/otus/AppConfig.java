package ru.otus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.QueationDao;
import ru.otus.dao.impl.QuestionDaoCSV;
import ru.otus.mapper.QuestionMapperCsv;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${test.file_name}")
    private String testFilePath;

    @Bean
    public QuestionMapperCsv questionMapperCsv(){
        return new QuestionMapperCsv();
    }

    @Bean
    public QueationDao queationDao(){
        return new QuestionDaoCSV(questionMapperCsv(), testFilePath);
    }
}
