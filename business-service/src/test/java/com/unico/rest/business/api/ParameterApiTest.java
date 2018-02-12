package com.unico.rest.business.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unico.rest.authentication.AuthenticationConfig;
import com.unico.rest.business.service.ParameterService;
import com.unico.rest.common.CommonConfig;
import com.unico.rest.common.jwt.JwtTokenUtil;
import com.unico.rest.common.request.ParameterRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by baybora on 2/7/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "dev")
@ContextConfiguration(classes = {CommonConfig.class,
        AuthenticationConfig.class,
        ApiConfig.class})
public class ParameterApiTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final String TEST_USERNAME = "user";

    private MockMvc mockMvc;

    @MockBean
    private ParameterService parameterService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String accessToken;
    private ParameterRequest parametersRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        //given
        accessToken = createAccessToken();
        parametersRequest = createParameterRequest();

    }

    @Test
    public void shouldUnauthorizedAddParametersEndpoint() throws Exception {
        mockMvc.perform(post(ParameterApi.ADD_PARAMETERS_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldSucceedAddParametersEndpoint() throws Exception {
        //given in setup

        //when
        when(parameterService
                .addParameters(parametersRequest))
                .thenReturn(true);

        mockMvc.perform(put(ParameterApi.ADD_PARAMETERS_URL)
                .content(new ObjectMapper().writeValueAsString(parametersRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())

                //then
                .andExpect(status().isOk())
                .andExpect(content().string(ParameterApi.ADD_PARAMETERS_SUCCESS_MESSAGE));
    }

    @Test
    public void shouldFailureAddParametersEndpoint() throws Exception {
        //given in setup

        //when
        when(parameterService
                .addParameters(parametersRequest))
                .thenReturn(false);

        mockMvc.perform(put(ParameterApi.ADD_PARAMETERS_URL)
        .content(createParameterRequestJson(parametersRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization","Bearer " + accessToken))
                .andDo(print())

                //then
        .andExpect(status().isInternalServerError())
                .andExpect(content().string(ParameterApi.ADD_PARAMETERS_FAILURE_MESSAGE));


    }

    private ParameterRequest createParameterRequest(){
        return ParameterRequest.builder().parameter1(1).parameter2(2).build();
    }

    private String createParameterRequestJson(ParameterRequest parametersRequest) throws Exception{
        return new ObjectMapper().writeValueAsString(parametersRequest);
    }


    private String createAccessToken(){
        return jwtTokenUtil.generateToken(new UserDetailsDummy(TEST_USERNAME));
    }
}

