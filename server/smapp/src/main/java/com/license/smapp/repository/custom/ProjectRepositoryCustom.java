package com.license.smapp.repository.custom;

import com.license.smapp.model.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    void detach(List<Project> projects);
}
