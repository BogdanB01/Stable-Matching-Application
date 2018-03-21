package com.license.smapp.service.impl;

import com.license.smapp.exception.EmailAlreadyTakenException;
import com.license.smapp.exception.ResourceNotFoundException;
import com.license.smapp.model.Grade;
import com.license.smapp.model.Role;
import com.license.smapp.model.Student;
import com.license.smapp.model.User;
import com.license.smapp.repository.RoleRepository;
import com.license.smapp.repository.StudentRepository;
import com.license.smapp.repository.UserRepository;
import com.license.smapp.service.StudentService;
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
        //check if email is already taken
        User user = this.userRepository.findByEmail(entity.getEmail());

        if(user != null) {
            throw new EmailAlreadyTakenException(user.getEmail(), "Email already taken");
        }

        //get student role from database
        Role role = roleRepository.findOne(3L);
        entity.setRoles(Collections.singletonList(role));
        //save the student
        return this.studentRepository.save(entity);
    }

    @Override
    public Student findById(Long id) {
        Student student = studentRepository.findOne(id);
        if(student == null) {
            throw new ResourceNotFoundException(id, "Student not found");
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if(findById(id) != null) {
            studentRepository.delete(id);
        }
    }

    @Override
    public List<Grade> getGradesForStudent(Long id) {
        Student student = findById(id);
        return student.getGrades();
    }
}