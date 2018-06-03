package com.license.smapp.service;

import com.license.smapp.model.Project;
import com.license.smapp.model.Student;

import java.util.Map;

public interface MatchingService {

    Map<Student, Project> match();
}
