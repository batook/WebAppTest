package com.batook.ex2.web;

import com.batook.ex2.api.MqServiceTemplateImpl;
import com.batook.ex2.data.JpaRepository;
import com.batook.ex2.data.entity.Banner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AmqpController {
    public static final Logger log = LoggerFactory.getLogger(AmqpController.class);

    @Autowired
    JpaRepository jpaRepository;

    @Autowired
    MqServiceTemplateImpl service;


    @RequestMapping(value = "/mq1",
                    method = RequestMethod.GET)
    public String helloMQ(ModelMap model) {
        log.info("AMQP");
        List<String> list = new ArrayList<>();
        list.add("Test AMQP");

        model.addAttribute("list", list);
        model.addAttribute("message", "AMQP: " + Calendar.getInstance()
                                                         .getTime());
        return "hello";
    }

    @RequestMapping(value = "/mq2",
                    method = RequestMethod.GET)
    public String helloMQTemplate(ModelMap model) {
        log.info("AMQP");
        List<Banner> list = jpaRepository.getBanners();
        log.info("Banners {}", list);
        List<String> result = list.stream()
                                  .map(e -> service.processMessage(e))
                                  .collect(Collectors.toList());
        log.info("Result {}", result);
        model.addAttribute("list", result);
        model.addAttribute("message", "AMQP: " + Calendar.getInstance()
                                                         .getTime());
        return "hello";
    }
}
