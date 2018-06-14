package com.license.smapp.control.service;

import com.license.smapp.entity.model.Grade;
import com.license.smapp.entity.model.Student;

import java.util.List;

public interface StudentService extends CrudService<Student>{
    List<Grade> getGradesForStudent(Long id);
    Student findStudentByName(String name);
    void update(Student student);
    List<Student> searchStudent(String searchTerm);
    void flush();
}
