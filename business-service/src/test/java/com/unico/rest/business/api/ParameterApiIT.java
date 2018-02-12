package com.unico.rest.business.api;

import com.unico.rest.authentication.AuthenticationConfig;
import com.unico.rest.business.service.ServiceConfig;
import com.unico.rest.common.CommonConfig;
import com.unico.rest.common.jwt.JwtTokenUtil;
import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.common.util.URLUtil;
import com.unico.rest.data.RepositoryConfig;
import com.unico.rest.jms.config.JmsReceiverConfig;
import com.unico.rest.jms.config.JmsSenderConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created by baybora on 2/9/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {CommonConfig.class,
        ApiConfig.class,
        AuthenticationConfig.class,
        ServiceConfig.class,
        JmsSenderConfig.class,
        JmsReceiverConfig.class,
        RepositoryConfig.class
})
@DirtiesContext
public class ParameterApiIT {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final String TEST_USERNAME = "user";

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private URLUtil urlUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void shouldSucceedParameterApi(){

        String accessToken = jwtTokenUtil.generateToken(new UserDetailsDummy(TEST_USERNAME));

        ParameterRequest parametersRequest = ParameterRequest.builder().parameter1(1).parameter2(2).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer " + accessToken);
        HttpEntity<ParameterRequest> entity = new HttpEntity(parametersRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(urlUtil.createEndpoint(localServerPort,ParameterApi.ADD_PARAMETERS_URL),
                HttpMethod.PUT,
                entity,
                String.class);

        HttpStatus httpStatus = response.getStatusCode();
        String responseBody = response.getBody();
    }

}
