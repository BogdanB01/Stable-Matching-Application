package com.license.smapp.service.impl;

import com.license.smapp.model.Student;
import com.license.smapp.model.User;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Service
public class HibernateSearchServiceImpl {

    @Autowired
    private final EntityManager entityManager;

    @Autowired
    public HibernateSearchServiceImpl(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    public void initializeHibernateSearch() {

        try {

            FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public List<Student> fuzzySearchStudents(String searchTerm) {

        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);

        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Student.class).get();

        Query luceneQuery = qb.keyword().fuzzy()
                            .withEditDistanceUpTo(2)
                            .withPrefixLength(1)
                            .onFields("name")
                            .matching(searchTerm)
                            .createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Student.class);

        // execute search

        List<Student> students = null;
        try {
            students = jpaQuery.getResultList();
        } catch (NoResultException e) {

        }
        return students;
    }
}
