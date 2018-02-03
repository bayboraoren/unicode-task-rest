package com.unicode.test.rest.api;

import com.unicode.test.rest.helper.EchoApiTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by baybora on 2/3/18.
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(EchoApiImpl.class)
public class EchoApiUnitTest implements EchoApiTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Override
    public void echoPingEndpointCalledSuccessfully() throws Exception{

        //when
        mockMvc.perform(get(EchoApi.ECHO_TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))
        //then
                .andExpect(status().isOk()).andDo(print());

    }

    @Test
    @Override
    public void echoPingEndpointReturnsSuccessMessage() throws Exception{

        //when
        String expectedMessage = EchoApi.ECHO_TEST_SUCCESS_MESSAGE;

        mockMvc.perform(get(EchoApi.ECHO_TEST_URL)
                .contentType(MediaType.APPLICATION_JSON))

        //then
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage)).andDo(print());
    }

}
