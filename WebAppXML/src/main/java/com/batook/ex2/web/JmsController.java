package com.batook.ex2.web;

import com.batook.ex2.api.JmsServiceImpl;
import com.batook.ex2.api.JmsServiceTemplateImpl;
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
public class JmsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmsController.class);

    @Autowired
    JpaRepository jpaRepository;

    @Autowired
    JmsServiceImpl jms;

    @Autowired
    JmsServiceTemplateImpl jmst;

    @RequestMapping(value = "/jms1",
                    method = RequestMethod.GET)
    public String helloJMS(ModelMap model) {
        LOGGER.info("JMS");
        List<String> list = new ArrayList<>();
        list.add("Test JMS");
        String msg = jms.processMessage("Test");
        list.add(msg);
        model.addAttribute("list", list);
        model.addAttribute("message", "JMS: " + Calendar.getInstance()
                                                        .getTime());
        return "hello";
    }

    @RequestMapping(value = "/jms2",
                    method = RequestMethod.GET)
    public String helloJMStemplate(ModelMap model) {
        LOGGER.info("JMStemplate");
        List<Banner> list = jpaRepository.getBanners();
        LOGGER.info("Banners {}", list);
        List<String> result = list.stream()
                                  .map(e -> jmst.processMessage(e))
                                  .collect(Collectors.toList());
        LOGGER.info("Result {}", result);
        model.addAttribute("list", result);
        model.addAttribute("message", "JMStemplate: " + Calendar.getInstance()
                                                                .getTime());
        return "hello";
    }

    @RequestMapping(value = "/jms3",
                    method = RequestMethod.GET)
    public String helloJMStemplateConvert(ModelMap model) {
        LOGGER.info("JMStemplateConvert");
        List<Banner> list = jpaRepository.getBanners();
        LOGGER.info("Banners {}", list);
        List<String> result = list.stream()
                                  .map(e -> jmst.processMessageConvert(e))
                                  .collect(Collectors.toList());
        LOGGER.info("Result {}", result);
        model.addAttribute("list", result);
        model.addAttribute("message", "JMStemplateConvert: " + Calendar.getInstance()
                                                                       .getTime());
        return "hello";
    }
}
