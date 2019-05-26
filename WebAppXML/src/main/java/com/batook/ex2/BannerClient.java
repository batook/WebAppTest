package com.batook.ex2;

import com.batook.ex2.dto.BannerRequest;
import com.batook.ex2.dto.BannerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class BannerClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(BannerClient.class);

    public BannerResponse getBanner(String id) {
        BannerRequest request = new BannerRequest();
        request.setId(id);
        log.info("Requesting location for " + id);

//        String uri = "http://localhost:9999/ws/bannerService";
//        WebServiceMessageCallback callback = new SoapActionCallback("http://batook.com/ex2/schemas/GetBannerRequest");
//        GetBannerResponse response = (GetBannerResponse) getWebServiceTemplate().marshalSendAndReceive(uri, request, callback);
        BannerResponse response = (BannerResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        log.info("response {}", response.getBanner1()
                                        .getValue());
        return response;
    }
}
