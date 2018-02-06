package com.unicode.test.rest.api;

import com.unicode.test.rest.BusinessApplication;
import com.unicode.test.rest.helper.ApiIntegrationTestHelper;
import com.unicode.test.rest.helper.EchoApiTestHelper;
import com.unicode.test.rest.util.URLUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by baybora on 2/3/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {BusinessApplication.class})
@TestPropertySource(locations="classpath:test.properties")
@ActiveProfiles(value = "testwithoutsecurity")
public class EchoApiIntegrationTest extends ApiIntegrationTestHelper implements EchoApiTestHelper {


    @Autowired
    private URLUtil urlUtil;

    @LocalServerPort
    private int randomPort;

    private String echoPingEndpointUrl;

    @Before
    public void init(){
        echoPingEndpointUrl = urlUtil.createEndpoint(randomPort,EchoApi.ECHO_TEST_URL);
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
