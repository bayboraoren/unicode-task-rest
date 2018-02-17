package com.unico.rest.business;

import com.unico.rest.authentication.AuthenticationConfig;
import com.unico.rest.business.api.ApiConfig;
import com.unico.rest.business.service.ServiceConfig;
import com.unico.rest.common.CommonConfig;
import com.unico.rest.data.RepositoryConfig;
import com.unico.rest.jms.config.JmsReceiverConfig;
import com.unico.rest.jms.config.JmsSenderConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by baybora on 2/10/18.
 */
@Configuration
@EnableAsync
@ImportAutoConfiguration(classes = {CommonConfig.class,
        ApiConfig.class,
        RepositoryConfig.class,
        AuthenticationConfig.class,
        ServiceConfig.class,
        JmsSenderConfig.class,
        JmsReceiverConfig.class,
        })
public class BusinessConfig {

        @Bean(name="TASK_EXECUTER")
        public TaskExecutor workExecutor() {
                ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
                threadPoolTaskExecutor.setThreadNamePrefix("Async-");
                threadPoolTaskExecutor.setCorePoolSize(3);
                threadPoolTaskExecutor.setMaxPoolSize(3);
                threadPoolTaskExecutor.setQueueCapacity(600);
                threadPoolTaskExecutor.afterPropertiesSet();
                return threadPoolTaskExecutor;
        }

}
