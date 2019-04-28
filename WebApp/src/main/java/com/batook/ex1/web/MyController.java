package com.batook.ex1.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class MyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

    @RequestMapping(value = "/hello",
                    method = GET)
    public String showHello(ModelMap model) {
        LOGGER.info("hello");
        model.addAttribute("message", "Hello!!!");
        return "hello";
    }

    @RequestMapping(value = "/register",
                    method = GET)
    public String showRegistrationForm() {
        return "register";
    }

    @RequestMapping(value = "/register",
                    method = POST)
    public String processRegistration(User user) {
        LOGGER.info("username {}", user.getUsername());
        return "redirect:/" + user.getUsername();
    }

    @RequestMapping(value = "/{username}",
                    method = GET)
    public String showSpitterProfile(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        return "profile";
    }
}


