package com.unico.rest.jms;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.data.RepositoryConfig;
import com.unico.rest.data.model.Parameter;
import com.unico.rest.data.repository.ParameterRepository;
import com.unico.rest.jms.config.JmsReceiverConfig;
import com.unico.rest.jms.config.JmsSenderConfig;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


/**
 * Created by baybora on 2/9/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("dev")
@ContextConfiguration(classes = {RepositoryConfig.class,
        JmsSenderConfig.class,
        JmsReceiverConfig.class})
@DirtiesContext
public class JmsUnitTest {

    @ClassRule
    public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

    @Autowired
    private JmsSender jmsSender;

    @Autowired
    private JmsReceiver jmsReceiver;

    @MockBean
    private ParameterRepository parameterRepository;


    @Test
    public void shouldSucceedAddParameters() throws Exception {
        ParameterRequest parametersRequest = ParameterRequest.builder().parameter1(1).parameter2(2).build();
        Date date = new Date();
        Parameter parameter = new Parameter(1L,1,2,date);
        Parameter retParameter = new Parameter(1L,1,2,date);
        when(parameterRepository.save(parameter)).thenReturn(retParameter);

        jmsSender.send(parametersRequest);
        jmsReceiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(jmsReceiver.getLatch().getCount(),is(0L));
    }

}
