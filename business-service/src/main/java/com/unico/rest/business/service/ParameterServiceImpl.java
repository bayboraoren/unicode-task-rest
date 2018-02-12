package com.unico.rest.business.service;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.jms.JmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by baybora on 2/9/18.
 */
@Service
public class ParameterServiceImpl implements ParameterService{

    @Autowired
    private JmsSender jmsSender;

    @Override
    public boolean addParameters(ParameterRequest parametersRequest) {
        return jmsSender.send(parametersRequest);
    }
}
