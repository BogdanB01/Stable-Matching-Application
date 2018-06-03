package com.license.smapp.entity.repository.custom;

import com.license.smapp.entity.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
