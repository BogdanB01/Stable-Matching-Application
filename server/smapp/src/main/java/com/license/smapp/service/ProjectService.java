package com.license.smapp.service;

import com.license.smapp.dto.UpdateProjectDTO;
import com.license.smapp.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface ProjectService extends CrudService<Project>{
    Page<Project> listAllByPage(Pageable pageable);
    Project update(Project project, Project model);
}
