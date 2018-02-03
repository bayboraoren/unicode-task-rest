package com.unicode.test.rest.helper;

import com.unicode.test.rest.api.EchoApi;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by baybora on 2/3/18.
 *
 */
public class ApiIntegrationTestHelper {

    @Autowired
    private RestTemplate restTemplate;

    protected ResponseEntity<String> callEndpoint(String endpointUrl,Class<String> clazz){
        return restTemplate.getForEntity(endpointUrl,clazz);
    }


    protected void assertThatHttpStatusCodeIsOK(ResponseEntity<String> responseEntity){
        assertThat(responseEntity.getStatusCode(),is(HttpStatus.OK));
    }

    protected void assertThatReturnMessageIsSuccess(ResponseEntity<String> responseEntity){
        assertThat(responseEntity.getBody(), Matchers.is(EchoApi.ECHO_TEST_SUCCESS_MESSAGE));
    }


}
