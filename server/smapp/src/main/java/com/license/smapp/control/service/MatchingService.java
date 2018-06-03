package com.license.smapp.control.service;

import com.license.smapp.entity.model.Project;
import com.license.smapp.entity.model.Student;

import java.util.Map;

public interface MatchingService {

    Map<Student, Project> match();
}
