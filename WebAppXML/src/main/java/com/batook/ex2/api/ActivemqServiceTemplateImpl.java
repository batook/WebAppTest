package com.batook.ex2.api;

import com.batook.ex2.data.entity.Banner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.JmsUtils;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

@Service
public class ActivemqServiceTemplateImpl {
    public static final Logger log = LoggerFactory.getLogger(ActivemqServiceTemplateImpl.class);

    @Autowired
    JmsOperations operations;

    public String processMessage(Banner banner) {
        operations.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                banner.setLine(banner.getLine() + "In");
                log.info("Send {}", banner.getLine());
                return session.createObjectMessage(banner);
            }
        });
        try {
            ObjectMessage message = (ObjectMessage) operations.receive();
            Banner b = (Banner) message.getObject();
            b.setLine(b.getLine() + "Out");
            log.info("Receive {}", b);
            return b.getLine();
        } catch (JMSException e) {
            throw JmsUtils.convertJmsAccessException(e);
        }
    }
}