package com.batook.ex2.web;

import com.batook.ex2.BannerClient;
import com.batook.ex2.data.HibernateRepository;
import com.batook.ex2.data.JdbcRepository;
import com.batook.ex2.data.JpaRepository;
import com.batook.ex2.data.entity.Banner;
import com.batook.ex2.schemas.BannerResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

    //WSDL
    //curl --header "Accept: text/html" http://localhost:9999/web/w
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

    // RESTful method
    //curl --header "Accept: application/json" http://localhost:9999/web/rest
    @RequestMapping(value = "/rest",
                    method = RequestMethod.GET,
                    produces = {"application/json", "application/xml"})
    //@ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Banner> getBanners() {
        LOGGER.info("rest");
        List<Banner> list = jpaRepository.getBanners();
        return list;
    }

    @RequestMapping(value = "/r1",
                    method = RequestMethod.GET)
    public String helloRest(ModelMap model) throws IOException {
        LOGGER.info("Rest");
        String URI = "http://localhost:9999/web/rest";
        HttpClient client = HttpClients.createDefault();
        HttpGet req = new HttpGet(URI);
        req.setHeader("Accept", "application/json");
        HttpResponse response = client.execute(req);
        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        //        String content = new BufferedReader(new InputStreamReader(entity.getContent())).lines().collect(Collectors.joining("\n"));
        //        LOGGER.info("content {}", content);
        TypeReference<List<Banner>> typeReference = new TypeReference<List<Banner>>() {
        };
        model.addAttribute("list", mapper.readValue(entity.getContent(), typeReference));
        model.addAttribute("message", "Rest: " + Calendar.getInstance()
                                                         .getTime());
        return "hello";
    }

    @RequestMapping(value = "/r2",
                    method = RequestMethod.GET)
    public String helloRestTemplate(ModelMap model) throws IOException {
        LOGGER.info("RestTemplate");
        String URI = "http://localhost:9999/web/rest";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
        LOGGER.info("getHeaders {}", entity.getHeaders()
                                           .entrySet());
        ParameterizedTypeReference typeReference = new ParameterizedTypeReference<List<Banner>>() {
        };

        ResponseEntity<List<Banner>> response = restTemplate.exchange(URI, HttpMethod.GET, entity, typeReference);

        model.addAttribute("list", response.getBody());
        model.addAttribute("message", "RestTemplate: " + Calendar.getInstance()
                                                         .getTime());
        return "hello";
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        return converters;
    }
}

