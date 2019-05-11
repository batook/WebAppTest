package com.batook.ex2.cxf;

import com.batook.ex2.data.JpaRepository;
import com.batook.ex2.data.entity.Banner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import java.util.List;

@Service
@Path("say")
public class HelloService {
    public static final Logger log = LoggerFactory.getLogger(HelloService.class);

    @Autowired
    JpaRepository jpaRepository;

    @Produces({"application/json", "plain/text"})
    @GET
    public List<Banner> saySomething(@QueryParam("words") @DefaultValue("Hello!") String words) {
        log.info("HelloResource {}", words);
        List<Banner> list = jpaRepository.getBanners();
        Banner banner = new Banner();
        banner.setLine(words);
        list.add(banner);
        return list;
    }
}
