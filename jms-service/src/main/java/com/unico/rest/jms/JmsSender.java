package com.unico.rest.jms;

import com.unico.rest.common.request.ParameterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

import java.util.Date;

/**
 * Created by baybora on 2/9/18.
 */
public class JmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${spring.queue.helloworld}")
    private String destination;

    public boolean send(ParameterRequest parametersRequest) {
        LOGGER.info("sending message='{}' to destination='{}'", parametersRequest, destination);
        try {
            parametersRequest.setInsertTimestamp(new Date());
            jmsTemplate.convertAndSend(destination, parametersRequest);
            return true;
        }catch (JmsException jmsException){
            LOGGER.error(jmsException.getMessage(),jmsException);
            return false;
        }

    }
}