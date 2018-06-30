package com.license.smapp.control.service;

import com.license.smapp.entity.model.Lecturer;
import com.license.smapp.entity.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService extends CrudService<Project>{
    Page<Project> listAllByPage(String filter, Pageable pageable);
    Project update(Project project, Project model);
    List<Project> getProjectsForLecturer(Lecturer lecturer, boolean active);
    Page<Project> listAllProjectsByActive(boolean active, Pageable pageable);
    Page<Project> filterActiveProjectsByProjectTitle(boolean active, String filter, Pageable pageable);
    Page<Project> filterActiveProjectsByLecturerName(boolean active, String filter, Pageable pageable);
    Page<Project> filterActiveProjectsByTagName(boolean active, String filter, Pageable pageable);
    void setAllProjectsInactive();

}
