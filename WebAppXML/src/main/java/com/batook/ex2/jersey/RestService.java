package com.batook.ex2.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/hello")
public class RestService {
    @GET
    @Produces("text/plain")
    public Response hello() {
        return Response.status(200)
                       .entity("Hello Jersey")
                       .build();
    }
}
