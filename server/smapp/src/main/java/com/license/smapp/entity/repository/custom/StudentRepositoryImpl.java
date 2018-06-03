package com.license.smapp.entity.repository.custom;


import com.license.smapp.entity.model.Student;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void detach(List<Student> students) {
        for (Student student : students) {
            entityManager.detach(student);
        }
    }
}
