package com.batook.ex2.data;

import com.batook.ex2.data.entity.Banner;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@Transactional(transactionManager = "hibernateTransactionManager")
public class HibernateRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateRepository.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Cacheable(value = "Banners")
    public List<Banner> findAll() {
//        Transactional
//        List<Banner> list = (List<Banner>) sessionFactory.getCurrentSession()
//                                                         .createCriteria(Banner.class)
//                                                         .list();
        List<Banner> list = (List<Banner>) sessionFactory.openSession()
                                                         .createCriteria(Banner.class)
                                                         .list();
        LOGGER.info("Hibernate list {}", list);
        return list;
    }
}
