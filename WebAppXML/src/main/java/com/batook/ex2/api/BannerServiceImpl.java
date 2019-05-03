package com.batook.ex2.api;

import com.batook.ex2.data.JdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    private static final Logger logger = LoggerFactory.getLogger(BannerServiceImpl.class);

    @Autowired
    JdbcRepository repository;

    @Override
    public List<String> getBanners() {
        List<String> list = null;
        try {
            list = repository.getBanners();
            logger.info("BannerServiceImpl {}", list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
