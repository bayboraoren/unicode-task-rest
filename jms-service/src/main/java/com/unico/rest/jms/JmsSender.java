package com.unico.rest.jms;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.data.model.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Date;

/**
 * Created by baybora on 2/9/18.
 */
public class JmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${jms.inbound}")
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