package com.license.smapp.service.impl;

import com.license.smapp.model.Grade;
import com.license.smapp.repository.GradeRepository;
import com.license.smapp.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    GradeRepository gradeRepository;

    @Override
    public Grade save(Grade entity) {
        return gradeRepository.save(entity);
    }

    @Override
    public Grade findById(Long id) {
        return gradeRepository.findOne(id);
    }

    @Override
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        gradeRepository.delete(id);
    }
}
