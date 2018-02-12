package com.unico.rest.business.api;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.business.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by baybora on 2/7/18.
 */
@RestController
public class ParameterApiImpl implements ParameterApi{

    @Autowired
    private ParameterService parameterService;

    @PutMapping(value = ADD_PARAMETERS_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<String> addParameters(@RequestBody ParameterRequest parametersRequest) {
        boolean success = parameterService.addParameters(parametersRequest);
        if(success) {
            return ResponseEntity.ok().body(ADD_PARAMETERS_SUCCESS_MESSAGE);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ADD_PARAMETERS_FAILURE_MESSAGE);
    }
}
