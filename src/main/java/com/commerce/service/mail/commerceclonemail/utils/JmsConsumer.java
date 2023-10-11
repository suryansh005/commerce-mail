package com.commerce.service.mail.commerceclonemail.utils;

import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;

@Component
public class JmsConsumer {

    @JmsListener(destination = "webapp-notification", containerFactory = "jmsListenerContainerFactoryQueue")
    public void queueMessageListener(ActiveMQTextMessage message){
        try {
//            ObjectMessage objectMessage = (ObjectMessage) message;

            if (message instanceof ActiveMQTextMessage) {
                ActiveMQTextMessage amqMessage = (ActiveMQTextMessage) message;
                System.out.println(amqMessage);
//                mqDelegate.execute(params, amqMessage.getText());
            }
            System.out.println(message);
//            System.out.println(objectMessage);
        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    @JmsListener(destination = "webapp-Topic", containerFactory = "jmsListenerContainerFactoryTopic")
    public void topicMessageListener(ActiveMQTextMessage message){
        try {
//            ObjectMessage objectMessage = (ObjectMessage) message;

            if (message instanceof ActiveMQTextMessage) {
                ActiveMQTextMessage amqMessage = (ActiveMQTextMessage) message;
                System.out.println(amqMessage);
//                mqDelegate.execute(params, amqMessage.getText());
            }
            System.out.println(message);
//            System.out.println(objectMessage);
        } catch (Exception ex){
            System.out.println(ex);
        }
    }
}
