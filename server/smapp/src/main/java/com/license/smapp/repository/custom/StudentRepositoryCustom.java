package com.license.smapp.repository.custom;

import com.license.smapp.model.Student;

import java.util.List;

public interface StudentRepositoryCustom {
    void detach(List<Student> student);
}
