package com.batook.ex2.jersey;

import com.batook.ex2.data.JpaRepository;
import com.batook.ex2.data.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/")
public class RestService {
    @Autowired
    private JpaRepository repository;

    @GET
    @Path("/hello")
    @Produces("text/plain")
    public Response hello() {
        return Response.status(200)
                       .entity("Hello Jersey")
                       .build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        List<Banner> list = repository.getBanners();
        return Response.status(200)
                       .entity(list)
                       .build();
    }
}
