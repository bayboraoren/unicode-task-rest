package com.unico.rest.business.service;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.data.model.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

/**
 * Created by baybora on 2/9/18.
 */
public interface ParameterService {
    boolean addParameters(ParameterRequest parametersRequest);
    @Async
    CompletableFuture<Page<Parameter>> getParameters(final Pageable pageable);
}
