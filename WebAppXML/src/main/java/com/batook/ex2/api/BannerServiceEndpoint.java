package com.batook.ex2.api;

import com.batook.ex2.schemas.GetBannerRequest;
import com.batook.ex2.schemas.GetBannerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

//http://localhost:9999/ws/bannerService/banner.wsdl

@Endpoint
public class BannerServiceEndpoint {
    private static final String NAMESPACE_URI = "http://batook.com/ex2/schemas";
    private static final Logger LOGGER = LoggerFactory.getLogger(BannerServiceEndpoint.class);

    @Autowired
    private BannerService service;

    @PayloadRoot(namespace = NAMESPACE_URI,
                 localPart = "getBannerRequest")
    @ResponsePayload
    public GetBannerResponse getBanner(@RequestPayload GetBannerRequest request) {
        LOGGER.info("request id: {}", request.getId());
        GetBannerResponse response = new GetBannerResponse();
        response.setBanner(service.getBanners()
                                  .get(0));

        return response;
    }
}
