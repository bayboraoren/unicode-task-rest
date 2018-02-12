package com.unico.rest.business.api;

import com.unico.rest.common.request.ParameterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by baybora on 2/7/18.
 */
public interface ParameterApi {
    String ADD_PARAMETERS_URL = "/api/v1/parameters";
    String ADD_PARAMETERS_SUCCESS_MESSAGE = "SUCCESS";
    String ADD_PARAMETERS_FAILURE_MESSAGE = "FAILURE";

    ResponseEntity<String> addParameters(@RequestBody ParameterRequest parametersRequest);
}
