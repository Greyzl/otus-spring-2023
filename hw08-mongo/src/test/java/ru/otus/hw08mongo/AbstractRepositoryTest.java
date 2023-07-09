package ru.otus.hw08mongo;



import io.mongock.driver.mongodb.springdata.v4.config.SpringDataMongoV4Context;
import io.mongock.runner.springboot.EnableMongock;
import io.mongock.test.springboot.junit5.MongockSpringbootJUnit5IntegrationTestBase;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

@DataMongoTest
@EnableMongock
@ImportAutoConfiguration(SpringDataMongoV4Context.class)
@EnableConfigurationProperties
@ComponentScan({"ru.otus.hw08mongo.persistance.repository"})
public abstract class AbstractRepositoryTest extends MongockSpringbootJUnit5IntegrationTestBase {
}
