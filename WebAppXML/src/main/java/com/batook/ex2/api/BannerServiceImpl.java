package com.batook.ex2.api;

import com.batook.ex2.data.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    JdbcRepository repository;

    @Override
    public List<String> getBanners() {
        List<String> list = null;
        try {
            list = repository.getBanners();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
