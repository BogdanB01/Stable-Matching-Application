package com.license.smapp.control.service;

import com.license.smapp.entity.model.Preference;
import com.license.smapp.entity.model.Project;
import com.license.smapp.entity.model.Student;

import java.util.List;

public interface PreferenceService extends CrudService<Preference>{
    Integer countAllByProjectAndStudent(Project project, Student student);
    List<Preference> findAllByProject(Project project);
}
