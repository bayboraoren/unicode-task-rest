package com.unico.rest.business.api;

import com.unico.rest.authentication.AuthenticationConfig;
import com.unico.rest.business.helper.ApiITHelper;
import com.unico.rest.business.helper.EchoApiTestHelper;
import com.unico.rest.business.service.ServiceConfig;
import com.unico.rest.common.util.URLUtil;
import com.unico.rest.common.CommonConfig;
import com.unico.rest.data.RepositoryConfig;
import com.unico.rest.jms.config.JmsReceiverConfig;
import com.unico.rest.jms.config.JmsSenderConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by baybora on 2/3/18.
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
public class EchoApiIT extends ApiITHelper implements EchoApiTestHelper {

    @Autowired
    private URLUtil urlUtil;

    @LocalServerPort
    private int localServerPort;

    private String echoPingEndpointUrl;

    @Before
    public void init(){
        echoPingEndpointUrl = urlUtil.createEndpoint(localServerPort,EchoApi.ECHO_TEST_URL);
    }

    @Test
    @Override
    public void echoPingEndpointCalledSuccessfully() throws Exception {
        ResponseEntity<String> responseEntity = callEndpoint(echoPingEndpointUrl,String.class);
        assertThatHttpStatusCodeIsOK(responseEntity);
    }

    @Test
    @Override
    public void echoPingEndpointReturnsSuccessMessage() throws Exception {
        ResponseEntity<String> responseEntity = callEndpoint(echoPingEndpointUrl,String.class);
        assertThatHttpStatusCodeIsOK(responseEntity);
        assertThatReturnMessageIsSuccess(responseEntity);
    }


}
