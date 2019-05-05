package com.batook.ex2.web;

import com.batook.ex2.BannerClient;
import com.batook.ex2.data.HibernateRepository;
import com.batook.ex2.data.JdbcRepository;
import com.batook.ex2.data.JpaRepository;
import com.batook.ex2.data.entity.Banner;
import com.batook.ex2.schemas.BannerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    //    @Autowired
    //    Jaxb2Marshaller marshaller;

    @Autowired
    BannerClient client;

    @RequestMapping(value = "/jdbc",
                    method = RequestMethod.GET)
    public String helloJDBC(ModelMap model) throws SQLException {
        LOGGER.info("hello");
        List<String> list = jdbcRepository.getBanners();
        model.addAttribute("list", list);
        model.addAttribute("message", "JDBC: " + Calendar.getInstance()
                                                         .getTime());
        return "hello";
    }

    @RequestMapping(value = "/jpa",
                    method = RequestMethod.GET)
    public String helloJPA(ModelMap model) {
        LOGGER.info("jpa");
        List<Banner> list = jpaRepository.getBanners();
        model.addAttribute("list", list);
        model.addAttribute("message", "JPA: " + Calendar.getInstance()
                                                        .getTime());
        return "hello";
    }

    @RequestMapping(value = "/h",
                    method = RequestMethod.GET)
    public String helloHibernate(ModelMap model) {
        LOGGER.info("hibernate");
        List<Banner> list = hibernateRepository.getBanners();
        model.addAttribute("list", list);
        model.addAttribute("message", "Hibernate: " + Calendar.getInstance()
                                                              .getTime());
        return "hello";
    }

    @RequestMapping(value = "/w",
                    method = RequestMethod.GET)
    public String helloWSDL(ModelMap model) {
        LOGGER.info("WSDL");
        //        BannerClient client = new BannerClient();
        //        client.setDefaultUri("http://localhost:9999/ws/bannerService");
        //        client.setMarshaller(marshaller);
        //        client.setUnmarshaller(marshaller);
        BannerResponse response = client.getBanner("1");
        model.addAttribute("list", response.getBanner1()
                                           .getValue());
        model.addAttribute("message", "WSDL: " + Calendar.getInstance()
                                                         .getTime());
        return "hello";
    }
}

