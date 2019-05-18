package com.batook.ex2.jersey;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        packages("com.batook.ex2.jersey");
        register(MyObjectMapperProvider.class);
        register(JacksonFeature.class);
    }
}
