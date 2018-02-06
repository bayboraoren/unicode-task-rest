package com.unicode.test.rest.api;

import com.unicode.test.rest.security.request.JwtAuthenticationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by baybora on 2/4/18.
 */
@Api(value = "AUTHENTICATION ENDPOINTS",
        description = "Authentication endpoints using to create token,refresh token from user via username and password",
        consumes="application/json",
        tags = {"AUTHENTICATION ENDPOINTS"})
public interface AuthenticationApi {
    @ApiOperation(value = "as a user, i want create token, so that i can use protected resources",
            response = String.class)
    ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException;
    @ApiOperation(value = "as a user, i want refresh old token, so that i can use protected resources again",
            response = String.class)
    ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request);
}
