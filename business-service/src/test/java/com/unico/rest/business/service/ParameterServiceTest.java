package com.unico.rest.business.service;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.common.response.ParameterResponse;
import com.unico.rest.data.model.Parameter;
import com.unico.rest.data.repository.ParameterRepository;
import com.unico.rest.jms.JmsSender;
import com.unico.rest.jms.config.JmsSenderConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Created by baybora on 2/9/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@ContextConfiguration(classes = {ServiceConfig.class,
        JmsSenderConfig.class})
public class ParameterServiceTest {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    private ParameterService parameterService;

    @MockBean
    private ParameterRepository parameterRepository;

    @MockBean
    private JmsSender jmsSender;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSucceedAddParameters() throws Exception {

        //given
        ParameterRequest parametersRequest = ParameterRequest.builder().parameter1(1).parameter2(2).build();

        //when
        when(jmsSender.send(parametersRequest)).thenReturn(true);

        //then
        boolean isSuccess = parameterService.addParameters(parametersRequest);
        assertThat(true, is(isSuccess));

    }

    @Test
    public void shouldSuceedGetParameters() throws Exception{
        //given
        ParameterResponse.builder().id(1L).parameter1(1).parameter2(2).insertTimestamp(new Date()).build();
        Pageable pageable = createPageable();

        //when
        doAnswer(answer -> CompletableFuture.completedFuture(createPageParameter()))
                .when(parameterRepository).findAllBy(pageable);

        CompletableFuture<Page<Parameter>> completableFuture = parameterService.getParameters(pageable);

        //then
        assertThat(true,is((completableFuture.get()).hasContent()));
    }

    private final int PAGE_NUMBER = 0;
    private final int PAGE_SIZE = 1;

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

    private Parameter createParameter() {
        return Parameter.builder().id(1L).parameter1(1).parameter2(2).insertTimestamp(new Date()).build();
    }




}
