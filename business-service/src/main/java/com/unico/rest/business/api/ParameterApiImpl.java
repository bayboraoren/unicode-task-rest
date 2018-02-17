package com.unico.rest.business.api;

import com.unico.rest.business.service.ParameterService;
import com.unico.rest.common.request.ParameterRequest;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * Created by baybora on 2/7/18.
 */
@RestController
public class ParameterApiImpl implements ParameterApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterApiImpl.class);

    @Autowired
    private ParameterService parameterService;

    @PutMapping(value = PARAMETERS_URL)
    @Override
    public ResponseEntity<String> addParameters(@RequestBody ParameterRequest parametersRequest) {
        boolean success = parameterService.addParameters(parametersRequest);
        if (success) {
            LOGGER.info("ADD PARAMETERS PROCESSED: " + parametersRequest.toString());
            return ResponseEntity.ok().body(ADD_PARAMETERS_SUCCESS_MESSAGE);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ADD_PARAMETERS_FAILURE_MESSAGE);
    }


    @GetMapping(value = PARAMETERS_URL)
    @Override
    public CompletableFuture<ResponseEntity> getParameters(final Pageable pageable) {
        CompletableFuture<ResponseEntity> responseEntityCompletableFuture = parameterService.getParameters(pageable).<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    LOGGER.error(GET_PARAMETERS_FAILURE_MESSAGE, throwable);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });

        return responseEntityCompletableFuture;
    }
}
