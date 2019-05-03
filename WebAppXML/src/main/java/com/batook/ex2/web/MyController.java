package com.batook.ex2.web;

import com.batook.ex2.data.entity.Banner;
import com.batook.ex2.data.HibernateRepository;
import com.batook.ex2.data.JdbcRepository;
import com.batook.ex2.data.JpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

@Controller
public class MyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

    @Autowired
    JdbcRepository jdbcRepository;

    @Autowired
    JpaRepository jpaRepository;

    @Autowired
    HibernateRepository hibernateRepository;

    @RequestMapping(value = "/jdbc",
                    method = RequestMethod.GET)
    public String helloJDBC(ModelMap model) throws SQLException, NamingException {
        LOGGER.info("hello");
        List<String> list = jdbcRepository.getBanners();
        model.addAttribute("list", list);
        model.addAttribute("message", "JDBC: " + Calendar.getInstance()
                                                         .getTime());
        return "hello";
    }

    @RequestMapping(value = "/jpa",
                    method = RequestMethod.GET)
    public String helloJPA(ModelMap model) throws SQLException, NamingException {
        LOGGER.info("jpa");
        List<Banner> list = jpaRepository.getBanners();
        model.addAttribute("list", list);
        model.addAttribute("message", "JPA: " + Calendar.getInstance()
                                                        .getTime());
        return "hello";
    }

    @RequestMapping(value = "/h",
                    method = RequestMethod.GET)
    public String helloHibernate(ModelMap model) throws SQLException, NamingException {
        LOGGER.info("hibernate");
        List<Banner> list = hibernateRepository.getBanners();
        model.addAttribute("list", list);
        model.addAttribute("message", "Hibernate: " + Calendar.getInstance()
                                                              .getTime());
        return "hello";
    }
}

