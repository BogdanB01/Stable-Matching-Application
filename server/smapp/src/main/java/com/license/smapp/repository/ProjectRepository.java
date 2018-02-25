package com.license.smapp.repository;

import com.license.smapp.model.Project;
import com.license.smapp.service.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

}
