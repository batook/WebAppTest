package com.batook.ex2.api;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class JmsServiceImpl implements JmsService {
    public static final Logger log = LoggerFactory.getLogger(JmsServiceImpl.class);

    @Autowired
    ActiveMQConnectionFactory connectionFactory;

    Connection connection;
    Session session;

    @Override
    public String processMessage(String msg) {
        send(msg);
        return receive();
    }

    public void send(String msg) {
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = new ActiveMQQueue("myQueue");
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage(msg + "In");
            log.info("Send {}", message.getText());
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
                if (connection != null) connection.close();
            } catch (JMSException ex) {

            }
        }
    }

    public String receive() {
        String msg = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = new ActiveMQQueue("myQueue");
            MessageConsumer consumer = session.createConsumer(destination);
            TextMessage message = (TextMessage) consumer.receive();
            msg = message.getText() + "Out";
            log.info("Receive {}", msg);
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
                if (connection != null) connection.close();
            } catch (JMSException ex) {

            }
        }
        return msg;
    }

}
