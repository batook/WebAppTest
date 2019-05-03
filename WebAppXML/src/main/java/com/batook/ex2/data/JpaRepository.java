package com.batook.ex2.data;

import com.batook.ex2.data.entity.Banner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JpaRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Cacheable(value = "Banners")
    public List<Banner> findAll() {
        List<Banner> list = (List<Banner>) entityManager.createQuery("select s from Banner s")
                                                        .getResultList();
        LOGGER.info("JPA list {}", list);
        return list;
    }

}

