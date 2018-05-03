package com.license.smapp.config;

import com.license.smapp.service.impl.HibernateSearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@EnableAutoConfiguration
@Configuration
public class HibernateSearchConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean
    HibernateSearchServiceImpl hibernateSearchServiceImpl() {

        HibernateSearchServiceImpl hibernateSearchService = new HibernateSearchServiceImpl(entityManager);
        hibernateSearchService.initializeHibernateSearch();
        return hibernateSearchService;
    }


}
