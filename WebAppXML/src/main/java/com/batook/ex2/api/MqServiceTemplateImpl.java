package com.batook.ex2.api;

import com.batook.ex2.data.entity.Banner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqServiceTemplateImpl {
    public static final Logger log = LoggerFactory.getLogger(JmsServiceTemplateImpl.class);

    @Autowired
    RabbitTemplate template;

    public String processMessage(Banner banner) {
        banner.setLine(banner.getLine() + "In");
        log.info("Send {}", banner.getLine());
        template.convertAndSend(banner);
        Banner b = (Banner) template.receiveAndConvert();
        return b.getLine() + "Out";
    }
}
