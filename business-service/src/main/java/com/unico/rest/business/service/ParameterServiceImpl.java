package com.unico.rest.business.service;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.data.model.Parameter;
import com.unico.rest.data.repository.ParameterRepository;
import com.unico.rest.jms.JmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Created by baybora on 2/9/18.
 */
@Service
public class ParameterServiceImpl implements ParameterService{

    @Autowired
    private JmsSender jmsSender;

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public boolean addParameters(ParameterRequest parametersRequest) {
        return jmsSender.send(parametersRequest);
    }

    @Override
    @Async("TASK_EXECUTER")
    public CompletableFuture<Page<Parameter>> getParameters(final Pageable pageable) {
        return parameterRepository.findAllBy(pageable);
    }
}
