package com.batook.ex2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class MyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "/hello",
                    method = RequestMethod.GET)
    public String showHello(ModelMap model) throws SQLException, NamingException {
        LOGGER.info("hello");
        List<String> list = new ArrayList<>();
        //        InitialContext ctx = new InitialContext();
        //        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDS");
        Locale.setDefault(Locale.ENGLISH);
        try (Connection conn = dataSource.getConnection(); Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("select * from v$version");
            while (rs.next()) {
                LOGGER.info(">>> {}", rs.getString(1));
                list.add(rs.getString(1));
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("message", "Hello!!!");
        return "hello";
    }
}

