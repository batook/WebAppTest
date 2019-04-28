package com.batook.ex2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String showHello(ModelMap model) {
        LOGGER.info("hello");
        model.addAttribute("message","Hello!!!");
        return "hello";
    }
}

