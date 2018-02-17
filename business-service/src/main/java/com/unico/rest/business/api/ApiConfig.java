package com.unico.rest.business.api;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * Created by baybora on 2/9/18.
 */
@Configuration
@ComponentScan(basePackages = {"com.unico.rest.business.api"})
@EnableSwagger2
@PropertySource(value = {"classpath:business-application.properties"})
public class ApiConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(Pageable.class, PagedResourcesAssembler.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build()
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo());

    }



    private Predicate<String> paths() {
        return Predicates.not(PathSelectors.regex("/error"));
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("UNICO REST API")
                .description("The REST API for unico code challenge.").termsOfServiceUrl("")
                .contact(new Contact("BORA OREN", "", "baybora.oren@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("0.0.1")
                .build();
    }


    private ApiKey apiKey() {
        return new ApiKey("api_key", "Authorization", "header");
    }


}
