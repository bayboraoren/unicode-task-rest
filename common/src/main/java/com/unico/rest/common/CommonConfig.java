package com.unico.rest.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by baybora on 2/7/18.
 */
@Configuration
@ComponentScan(basePackages = {"com.unico.rest.common"})
@PropertySource(value = {"file:/Users/baybora/Desktop/unico/unico-workspace/unicode-task-rest/common/src/main/resources/common-application.properties"})
public class CommonConfig {
}
