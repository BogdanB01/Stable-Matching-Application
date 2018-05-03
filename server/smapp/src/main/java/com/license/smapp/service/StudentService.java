package com.license.smapp.service;

import com.license.smapp.model.Grade;
import com.license.smapp.model.Preference;
import com.license.smapp.model.Project;
import com.license.smapp.model.Student;

import java.util.List;

public interface StudentService extends CrudService<Student>{
    List<Grade> getGradesForStudent(Long id);
    Student findStudentByName(String name);
    void update(Student student);
}
