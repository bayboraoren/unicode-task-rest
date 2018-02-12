package com.unico.rest.data;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by baybora on 2/10/18.
 */
@Configuration
@ComponentScan(basePackages = {"com.unico.rest.data"})
@EnableJpaRepositories(basePackages = {"com.unico.rest.data.repository"})
@EntityScan(basePackages = {"com.unico.rest.data.model"})
@PropertySource(value = "file:/Users/baybora/Desktop/unico/unico-workspace/unicode-task-rest/data-service/src/main/resources/application-data.properties")
@EnableTransactionManagement
public class RepositoryConfig {
}
