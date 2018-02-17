package com.unico.rest.business.api;

import com.unico.rest.business.helper.EchoApiTestHelper;
import com.unico.rest.business.service.ParameterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by baybora on 2/3/18.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EchoApiImpl.class)
@ContextConfiguration(classes = {ApiConfig.class})
@ActiveProfiles(value = "testwithoutsecurity")
public class EchoApiTest implements EchoApiTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParameterService parameterService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Override
    public void echoPingEndpointCalledSuccessfully() throws Exception {

        //when
        mockMvc.perform(get(EchoApi.ECHO_TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk()).andDo(print());

    }

    @Test
    @Override
    public void echoPingEndpointReturnsSuccessMessage() throws Exception {

        //when
        String expectedMessage = EchoApi.ECHO_TEST_SUCCESS_MESSAGE;

        mockMvc.perform(get(EchoApi.ECHO_TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))

                //then
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage)).andDo(print());
    }

}
