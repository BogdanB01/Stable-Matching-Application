package com.license.smapp.repository.custom;

import com.license.smapp.model.Project;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUtil;
import java.util.List;

@Repository
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger("SALUT");

    @Override
    public void detach(List<Project> projects) {
        for (Project project : projects) {
            entityManager.detach(project);
        }
    }

}
