package com.license.smapp.control.service.impl;

import com.license.smapp.entity.model.*;
import com.license.smapp.entity.repository.RoleRepository;
import com.license.smapp.entity.repository.StudentRepository;
import com.license.smapp.entity.repository.UserRepository;
import com.license.smapp.control.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Student save(Student entity)
    {
        //get student role from database
        Role role = roleRepository.findOne(3L);
        entity.setRoles(Collections.singletonList(role));
        //save the student
        return this.studentRepository.save(entity);
    }

    @Override
    public Student findById(Long id) {
        Student student = studentRepository.findOne(id);
        return student;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
    }

    @Override
    public List<Grade> getGradesForStudent(Long id) {
        Student student = findById(id);
        return student.getGrades();
    }

    @Override
    public Student findStudentByName(String name) {
        return this.studentRepository.findByName(name);
    }

    @Override
    public void update(Student student) {
        studentRepository.save(student);
    }

    @Override
    public List<Student> searchStudent(String searchTerm) {
        return studentRepository.findTop10ByNameIsContaining(searchTerm);
    }

}
