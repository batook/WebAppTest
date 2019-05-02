package com.batook.ex2.controller;

import com.batook.ex2.data.OraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class MyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

    @Autowired
    OraRepository oraRepository;

    @RequestMapping(value = "/hello",
                    method = RequestMethod.GET)
    public String showHello(ModelMap model) throws SQLException, NamingException {
        LOGGER.info("hello");
        List<String> list = oraRepository.getBanner();
        model.addAttribute("list", list);
        model.addAttribute("message", Calendar.getInstance().getTime());
        return "hello";
    }
}

