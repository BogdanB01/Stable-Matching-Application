package com.license.smapp.service;

import com.license.smapp.model.Preference;
import com.license.smapp.model.Project;
import com.license.smapp.model.Student;

import java.util.List;

public interface PreferenceService extends CrudService<Preference>{
    Integer countAllByProjectAndStudent(Project project, Student student);
    List<Preference> findAllByProject(Project project);
}
