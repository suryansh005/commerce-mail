package com.commerce.service.mail.commerceclonemail.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.util.Assert;

import javax.jms.*;
import java.util.Arrays;

@Configuration
public class MessageBrokerConfig implements DestinationResolver{

    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory
                = new ActiveMQConnectionFactory();

        activeMQConnectionFactory.setTrustedPackages(Arrays.asList("com.commerce.webapp"));
        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactoryQueue(){
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory
                = new DefaultJmsListenerContainerFactory();

        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory());
        defaultJmsListenerContainerFactory.setSubscriptionDurable(true);
        defaultJmsListenerContainerFactory.setClientId("IS340-queue");
        defaultJmsListenerContainerFactory.setDestinationResolver(this.destinationResolver());
        return defaultJmsListenerContainerFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactoryTopic(){
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory
                = new DefaultJmsListenerContainerFactory();

        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory());
        defaultJmsListenerContainerFactory.setSubscriptionDurable(true);
        defaultJmsListenerContainerFactory.setClientId("IS340-topic");
        defaultJmsListenerContainerFactory.setDestinationResolver(this.destinationResolver());
        return defaultJmsListenerContainerFactory;
    }


    @Bean
    public DestinationResolver destinationResolver(){
        return this::resolveDestinationName;
    }

    @Override
    public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException {
        Assert.notNull(session, "Session must not be null");
        Assert.notNull(destinationName, "Destination name must not be null");
        if (destinationName.endsWith("Topic")) {
            return resolveTopic(session, destinationName);
        }
        else {
            return resolveQueue(session, destinationName);
        }
    }

    Topic resolveTopic(Session session, String topicName) throws JMSException {
        return session.createTopic(topicName);
    }

    Queue resolveQueue(Session session, String queueName) throws JMSException {
        return session.createQueue(queueName);
    }
}
