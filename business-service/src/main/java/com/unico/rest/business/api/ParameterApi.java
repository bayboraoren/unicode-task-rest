package com.unico.rest.business.api;

import com.unico.rest.common.request.ParameterRequest;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.concurrent.CompletableFuture;

/**
 * Created by baybora on 2/7/18.
 */


@Api(value = "PARAMETER ENDPOINT",
        description = "Parameter endpoint has got add, get parameters methods.",
        consumes = "application/json",
        tags = {"PARAMETER ENDPOINT"})
public interface ParameterApi {

    String PARAMETERS_URL = "/api/v1/parameter";
    String ADD_PARAMETERS_SUCCESS_MESSAGE = "Add parameter process succeed";
    String ADD_PARAMETERS_FAILURE_MESSAGE = "Unable to add parameters";
    String GET_PARAMETERS_FAILURE_MESSAGE = "Unable to get parameter list";

    /////////////////////////////////// ADD PARAMETER ///////////////////////////////////////
    @ApiOperation(value = "as a user, I want to add 2 parameters, so that i can get list of parameters when i want",
            authorizations = {@Authorization("api_key")})
    @ApiParam
    ResponseEntity<String> addParameters(@ApiParam(name = "parameters", required = true)
                                         @RequestBody ParameterRequest parametersRequest);


    /////////////////////////////////// GET PARAMETERS /////////////////////////////////////
    @ApiOperation(value = "as a user, i want to list all parameters, so that i can list all parameters",
            authorizations = {@Authorization("api_key")},
            consumes = "application/json",
            produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", required = true),
            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query",
                    value = "Number of records per page.", required = true),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "JSON representation of collection of parameter"),
            @ApiResponse(code = 404, message = "No parameters found"),
            @ApiResponse(code = 500, message = "Something went wrong!"),
            @ApiResponse(code = 415, message = "Unsupported media type")
    }
    )
    CompletableFuture<ResponseEntity> getParameters(@ApiIgnore final Pageable paging);
}
