package com.license.smapp.service;

import com.license.smapp.model.Preference;
import com.license.smapp.model.Project;
import com.license.smapp.model.Student;

public interface PreferenceService extends CrudService<Preference>{
    Integer countAllByProjectAndStudent(Project project, Student student);
}
