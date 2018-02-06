package com.unicode.test.rest.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by baybora on 2/3/18.
 *
 */


@Api(value = "ECHO ENDPOINT",
            description = "Echo endpoint inform user that the system is online  (to initialize project for tech stack)",
        consumes="application/json",
        tags = {"ECHO ENDPOINT"})
public interface EchoApi {

    String ECHO_TEST_URL ="/api/v1/echo";
    String ECHO_TEST_SUCCESS_MESSAGE ="SUCCESS";

    @ApiOperation(value = "as a user, i want to call echo test endpoint, so that i can understand that endpoint infrastructure works",
            response = String.class)
    String test();
}
