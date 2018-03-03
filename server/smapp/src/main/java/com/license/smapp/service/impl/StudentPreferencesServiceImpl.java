package com.license.smapp.service.impl;

import com.license.smapp.model.StudentPreferences;
import com.license.smapp.repository.StudentPreferencesRepository;
import com.license.smapp.service.StudentPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentPreferencesServiceImpl implements StudentPreferencesService{

    @Autowired
    private StudentPreferencesRepository studentPreferencesRepository;


    @Override
    public StudentPreferences save(StudentPreferences entity) {
        return this.studentPreferencesRepository.save(entity);
    }

    @Override
    public StudentPreferences findById(Long id) {
        return this.studentPreferencesRepository.findOne(id);
    }

    @Override
    public List<StudentPreferences> findAll() {
        return this.studentPreferencesRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.studentPreferencesRepository.delete(id);
    }
}
