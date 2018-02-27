package com.license.smapp.service;

import com.license.smapp.model.Lecturer;
import com.license.smapp.model.Project;

import java.util.List;

public interface LecturerService extends CrudService<Lecturer> {
    Project addProject(Long lecturerId, Project project);
}
