package com.batook.ex2.web;

import com.batook.ex2.BannerClient;
import com.batook.ex2.api.ActivemqServiceImpl;
import com.batook.ex2.api.ActivemqServiceTemplateImpl;
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
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
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
import java.util.stream.Collectors;

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

    @Autowired
    ActivemqServiceImpl mq;

    @Autowired
    ActivemqServiceTemplateImpl mqt;

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
    public String helloRestTemplateCommon(ModelMap model) throws IOException {
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
        model.addAttribute("message", "RestTemplateCommon: " + Calendar.getInstance()
                                                                       .getTime());
        return "hello";
    }

    @RequestMapping(value = "/r3",
                    method = RequestMethod.GET)
    public String helloRestTemplateEntity(ModelMap model) throws IOException {
        LOGGER.info("RestTemplateEntity");
        String URI = "http://localhost:9999/web/rest";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());


        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(URI, Object[].class);
        MediaType contentType = responseEntity.getHeaders()
                                              .getContentType();
        HttpStatus statusCode = responseEntity.getStatusCode();
        Object[] objects = responseEntity.getBody();
        LOGGER.info("contentType {} ", contentType);
        LOGGER.info("statusCode {} ", statusCode);
        LOGGER.info("headers {}", responseEntity.getHeaders()
                                                .entrySet());
        model.addAttribute("list", objects);
        model.addAttribute("message", "RestTemplateEntity: " + Calendar.getInstance()
                                                                       .getTime());
        return "hello";
    }

    @RequestMapping(value = "/r4",
                    method = RequestMethod.GET)
    public String helloRestTemplateObject(ModelMap model) throws IOException {
        LOGGER.info("RestTemplateObject");
        String URI = "http://localhost:9999/web/rest";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());

        Object[] objects = restTemplate.getForObject(URI, Object[].class);

        model.addAttribute("list", objects);
        model.addAttribute("message", "RestTemplateObject: " + Calendar.getInstance()
                                                                       .getTime());
        return "hello";
    }

    @RequestMapping(value = "/r5",
                    method = RequestMethod.GET)
    public String helloRestTemplateList(ModelMap model) throws IOException {
        LOGGER.info("RestTemplateWrapper");
        String URI = "http://localhost:9999/web/rest";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());
        List<Banner> list = restTemplate.getForObject(URI, List.class);
        model.addAttribute("list", list);
        model.addAttribute("message", "RestTemplateList: " + Calendar.getInstance()
                                                                     .getTime());
        return "hello";
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new MappingJackson2XmlHttpMessageConverter());
        return converters;
    }

    @RequestMapping(value = "/mq1",
                    method = RequestMethod.GET)
    public String helloMQ(ModelMap model) {
        LOGGER.info("MQ");
        List<String> list = new ArrayList<>();
        list.add("Test");
        String msg = mq.processMessage("Test");
        list.add(msg);
        model.addAttribute("list", list);
        model.addAttribute("message", "MQ: " + Calendar.getInstance()
                                                       .getTime());
        return "hello";
    }

    @RequestMapping(value = "/mq2",
                    method = RequestMethod.GET)
    public String helloMQtemplate(ModelMap model) {
        LOGGER.info("MQtemplate");
        List<Banner> list = jpaRepository.getBanners();
        LOGGER.info("Banners {}", list);
        List<String> result = list.stream()
                                  .map(e -> mqt.processMessage(e))
                                  .collect(Collectors.toList());
        LOGGER.info("Result {}", result);
        model.addAttribute("list", result);
        model.addAttribute("message", "MQtemplate: " + Calendar.getInstance()
                                                               .getTime());
        return "hello";
    }

}
