package com.license.smapp.service.impl;

import com.license.smapp.model.Preference;
import com.license.smapp.model.Project;
import com.license.smapp.model.Student;
import com.license.smapp.repository.PreferenceRepository;
import com.license.smapp.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceServiceImpl implements PreferenceService {

    @Autowired
    PreferenceRepository preferenceRepository;

    @Override
    public Preference save(Preference entity) {
        return preferenceRepository.save(entity);
    }

    @Override
    public Preference findById(Long id) {
        return preferenceRepository.findOne(id);
    }

    @Override
    public List<Preference> findAll() {
        return preferenceRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        preferenceRepository.delete(id);
    }

    @Override
    public Integer countAllByProjectAndStudent(Project project, Student student) {
        Integer count = preferenceRepository.countAllByProjectAndStudent(project, student);

        return count;
    }
}
