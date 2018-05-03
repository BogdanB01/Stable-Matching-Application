package com.license.smapp.service.impl;

import com.license.smapp.exception.ResourceNotFoundException;
import com.license.smapp.model.Lecturer;
import com.license.smapp.model.Project;
import com.license.smapp.model.Role;
import com.license.smapp.model.User;
import com.license.smapp.repository.LecturerRepository;
import com.license.smapp.repository.ProjectRepository;
import com.license.smapp.repository.RoleRepository;
import com.license.smapp.repository.UserRepository;
import com.license.smapp.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LecturerServiceImpl implements LecturerService{

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Lecturer save(Lecturer entity) {
        //get lecturer role from database
        Role role = roleRepository.findOne(2L);
        entity.setRoles(Collections.singletonList(role));
        //save the entity
        return this.lecturerRepository.save(entity);
    }

    @Override
    public Lecturer findById(Long id) {
        Lecturer lecturer = this.lecturerRepository.findOne(id);
        return lecturer;
    }

    @Override
    public List<Lecturer> findAll() {
        return this.lecturerRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.lecturerRepository.delete(id);
    }

    @Override
    public Project addProject(Long lecturerId, Project project) {
        //get lecturer by id
        Lecturer lecturer = findById(lecturerId);

        project.setLecturer(lecturer);
        project = this.projectRepository.save(project);
        return project;
    }
}
