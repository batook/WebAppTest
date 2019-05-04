package com.batook.ex2;

import com.batook.ex2.schemas.GetBannerRequest;
import com.batook.ex2.schemas.GetBannerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class BannerClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(BannerClient.class);

    public GetBannerResponse getBanner(String id) {
        GetBannerRequest request = new GetBannerRequest();
        request.setId(id);
        log.info("Requesting location for " + id);

        String uri = "http://localhost:9999/ws/bannerService";
        WebServiceMessageCallback callback = new SoapActionCallback("http://batook.com/ex2/schemas/GetBannerRequest");
        GetBannerResponse response = (GetBannerResponse) getWebServiceTemplate().marshalSendAndReceive(uri, request, callback);
        log.info("response {}", response.getBanner1()
                                        .getValue());
        return response;
    }
}
