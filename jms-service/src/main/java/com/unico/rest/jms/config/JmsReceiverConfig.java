package com.unico.rest.jms.config;

import com.unico.rest.jms.JmsReceiver;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.net.URI;

/**
 * Created by baybora on 2/9/18.
 */
@Configuration
@EnableJms
@PropertySource(value = {"file:/Users/baybora/Desktop/unico/unico-workspace/unicode-task-rest/jms-service/src/main/resources/application-jms.properties"})
public class JmsReceiverConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        factory.setMessageConverter(messageConverter());
        factory.setConcurrency("5-20");

        return factory;
    }

    @Bean
    public JmsReceiver jmsReceiver() {
        return new JmsReceiver();
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public BrokerService createBrokerService() throws Exception {
        BrokerService broker = new BrokerService();
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI(brokerUrl));
        broker.addConnector(connector);
        return broker;
    }

}