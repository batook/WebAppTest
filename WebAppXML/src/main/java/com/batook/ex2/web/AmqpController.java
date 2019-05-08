package com.batook.ex2.web;

import com.batook.ex2.data.JpaRepository;
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

@Controller
public class AmqpController {
    public static final Logger log = LoggerFactory.getLogger(AmqpController.class);

    @Autowired
    JpaRepository jpaRepository;

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

}
