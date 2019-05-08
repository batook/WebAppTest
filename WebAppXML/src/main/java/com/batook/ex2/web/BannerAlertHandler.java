package com.batook.ex2.web;

import com.batook.ex2.data.entity.Banner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BannerAlertHandler {
    public static final Logger log = LoggerFactory.getLogger(BannerAlertHandler.class);

    public void handleAlert(Banner banner) {
        log.info(">>> banner Alert {}", banner.getLine());
    }
}
