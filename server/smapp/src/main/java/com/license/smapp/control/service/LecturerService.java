package com.license.smapp.control.service;

import com.license.smapp.entity.model.Lecturer;
import com.license.smapp.entity.model.Project;

public interface LecturerService extends CrudService<Lecturer> {
    Project addProject(Long lecturerId, Project project);
}
