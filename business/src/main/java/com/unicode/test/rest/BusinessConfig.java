package com.unicode.test.rest;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

/**
 * Created by baybora on 2/3/18.
 */
@Configuration
@ComponentScan(basePackages = {"com.unicode.test.rest"})
@EnableSwagger2
@Import(AuthenticationConfig.class)
public class BusinessConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build();
    }


    private Predicate<String> paths() {
        return Predicates.not(PathSelectors.regex("/error"));
    }


}
