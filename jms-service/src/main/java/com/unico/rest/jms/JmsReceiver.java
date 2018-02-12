package com.unico.rest.jms;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.data.mapper.ParametersMapper;
import com.unico.rest.data.model.Parameter;
import com.unico.rest.data.repository.ParameterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.concurrent.CountDownLatch;

/**
 * Created by baybora on 2/9/18.
 */
public class JmsReceiver {

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private ParametersMapper parametersMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsReceiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "${spring.queue.helloworld}")
    public void receive(@Payload ParameterRequest parametersRequest) {
        LOGGER.info("received message='{}'", parametersRequest);
        Parameter parameter = parametersMapper.parameterRequestToParameter(parametersRequest);
        parameterRepository.save(parameter);
        latch.countDown();
    }

}
