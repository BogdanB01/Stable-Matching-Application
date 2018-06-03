package com.license.smapp.entity.repository.custom;

import com.license.smapp.entity.model.Student;

import java.util.List;

public interface StudentRepositoryCustom {
    void detach(List<Student> student);
}
