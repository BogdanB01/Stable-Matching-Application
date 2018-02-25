package com.license.smapp.service.impl;

import com.license.smapp.model.Project;
import com.license.smapp.repository.ProjectRepository;
import com.license.smapp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project save(Project entity) {
        return this.projectRepository.save(entity);
    }

    @Override
    public Project findById(Long id) {
        return this.projectRepository.findOne(id);
    }

    @Override
    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.projectRepository.delete(id);
    }

    @Override
    public Page<Project> listAllByPage(Pageable pageable) {
        return this.projectRepository.findAll(pageable);
    }
}
