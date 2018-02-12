package com.unico.rest.business;

import com.unico.rest.authentication.AuthenticationConfig;
import com.unico.rest.business.api.ApiConfig;
import com.unico.rest.business.service.ServiceConfig;
import com.unico.rest.common.CommonConfig;
import com.unico.rest.data.RepositoryConfig;
import com.unico.rest.jms.config.JmsReceiverConfig;
import com.unico.rest.jms.config.JmsSenderConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Created by baybora on 2/10/18.
 */
@Configuration
@ImportAutoConfiguration(classes = {CommonConfig.class,
        ApiConfig.class,
        RepositoryConfig.class,
        AuthenticationConfig.class,
        ServiceConfig.class,
        JmsSenderConfig.class,
        JmsReceiverConfig.class,
        })
public class BusinessConfig {
}
