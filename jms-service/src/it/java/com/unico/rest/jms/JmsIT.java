package com.unico.rest.jms;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.data.RepositoryConfig;
import com.unico.rest.data.repository.ParameterRepository;
import com.unico.rest.jms.config.JmsReceiverConfig;
import com.unico.rest.jms.config.JmsSenderConfig;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by baybora on 2/10/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RepositoryConfig.class,
        JmsSenderConfig.class,
        JmsReceiverConfig.class})
public class JmsIT {

    @ClassRule
    public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private JmsSender jmsSender;

    @Autowired
    private JmsReceiver jmsReceiver;

    @Test
    public void shouldAddParametersToDatabase() throws InterruptedException {

        int param1 = 1,
                param2 = 2;


        ParameterRequest parametersRequest = ParameterRequest.builder()
                .parameter1(param1)
                .parameter2(param2)
                .build();

        boolean isSuccess = jmsSender.send(parametersRequest);
        assertThat(isSuccess, is(true));
        jmsReceiver.getLatch().await(2000, TimeUnit.MILLISECONDS);
        assertThat(parameterRepository.findOne(1L), is(notNullValue()));
    }
}
