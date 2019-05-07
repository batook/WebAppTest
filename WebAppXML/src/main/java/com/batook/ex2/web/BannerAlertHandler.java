package com.batook.ex2.web;

import com.batook.ex2.api.ActivemqServiceTemplateImpl;
import com.batook.ex2.data.entity.Banner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BannerAlertHandler {
    public static final Logger log = LoggerFactory.getLogger(BannerAlertHandler.class);

    public void handleAlert(Banner banner) {
        log.info(">>> banner Alert {}", banner.getLine());
    }
}
