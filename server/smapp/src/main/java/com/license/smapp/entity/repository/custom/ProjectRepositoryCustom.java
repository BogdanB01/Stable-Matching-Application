package com.license.smapp.entity.repository.custom;

import com.license.smapp.entity.model.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    void detach(List<Project> projects);
}
