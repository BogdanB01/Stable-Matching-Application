package com.license.smapp.control.service.impl;

import com.license.smapp.entity.model.Course;
import com.license.smapp.entity.repository.CourseRepository;
import com.license.smapp.control.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course save(Course entity) {
        return this.courseRepository.save(entity);
    }

    @Override
    public Course findById(Long id) {
        return this.courseRepository.findOne(id);
    }

    @Override
    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.courseRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        this.courseRepository.deleteAll();
    }
}
