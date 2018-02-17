package com.unico.rest.business.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unico.rest.authentication.AuthenticationConfig;
import com.unico.rest.business.service.ParameterService;
import com.unico.rest.common.CommonConfig;
import com.unico.rest.common.jwt.JwtTokenUtil;
import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.common.response.ParameterResponse;
import com.unico.rest.common.test.UserDetailsDummy;
import com.unico.rest.data.model.Parameter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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

    private Date date;

    @MockBean
    private ParameterService parameterService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver;

    private String accessToken;
    private ParameterRequest parametersRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        /*mockMvc = MockMvcBuilders
                .standaloneSetup(ParameterApiImpl.class)
                .apply(springSecurity())
                .setCustomArgumentResolvers(pageableHandlerMethodArgumentResolver)
                .build();*/

        mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        //given
        accessToken = createAccessToken();
        parametersRequest = createParameterRequest();
        date = new Date();
    }

    @Test
    public void shouldUnauthorizedAddParametersEndpoint() throws Exception {
        mockMvc.perform(post(ParameterApi.PARAMETERS_URL))
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

        mockMvc.perform(put(ParameterApi.PARAMETERS_URL)
                .content(createJsonString(parametersRequest))
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

        mockMvc.perform(put(ParameterApi.PARAMETERS_URL)
                .content(createJsonString(parametersRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())

                //then
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(ParameterApi.ADD_PARAMETERS_FAILURE_MESSAGE));


    }

    private final int PAGE_NUMBER = 0;
    private final int PAGE_SIZE = 1;

    @Test
    public void shouldSucceedGetParametersEndpoint() throws Exception {
        //given in setup
        Pageable pageable = createPageable();
        Page<Parameter> pageParameter = createPageParameter();

        //when
        doAnswer(answer -> CompletableFuture.completedFuture(pageParameter))
                .when(parameterService).getParameters(pageable);

        MvcResult mvcResult = mockMvc.perform(get(ParameterApi.PARAMETERS_URL)
                .param("size", pageable.getPageSize() + "")
                .param("page", pageable.getPageNumber() + "")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andReturn();

        mockMvc
                .perform(asyncDispatch(mvcResult))
                .andDo(print())

                //then
                .andExpect(status().isOk())
                .andExpect(content().json(createJsonString(pageParameter)));

    }


    private ParameterRequest createParameterRequest() {
        return ParameterRequest.builder().parameter1(1).parameter2(2).insertTimestamp(date).build();
    }

    private ParameterResponse createParameterResponse() {
        return ParameterResponse.builder().id(1L).parameter1(1).parameter2(2).insertTimestamp(date).build();
    }

    private Parameter createParameter() {
        return Parameter.builder().id(1L).parameter1(1).parameter2(2).insertTimestamp(new Date()).build();
    }

    private CompletableFuture<Page<Parameter>> createCompletableFuturePageParameter() {
        return CompletableFuture.completedFuture(createPageParameter());
    }

    private Sort createSort() {
        return new Sort(Sort.Direction.ASC, "ID");
    }

    private Pageable createPageable() {
        return new PageRequest(PAGE_NUMBER, PAGE_SIZE);
    }

    private Page<Parameter> createPageParameter() {
        return new PageImpl<>(createParameterList(), createPageable(), PAGE_NUMBER);
    }

    private List<Parameter> createParameterList() {
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(createParameter());
        return parameterList;
    }

    private String createJsonString(Object requestObject) throws Exception {
        return new ObjectMapper().writeValueAsString(requestObject);
    }


    private String createAccessToken() {
        return jwtTokenUtil.generateToken(new UserDetailsDummy(TEST_USERNAME));
    }


}

