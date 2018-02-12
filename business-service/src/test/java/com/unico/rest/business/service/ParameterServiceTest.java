package com.unico.rest.business.service;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.jms.JmsSender;
import com.unico.rest.jms.config.JmsSenderConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
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
    private JmsSender jmsSender;

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


}
