package com.license.StableMatchingApplication.repository;

import com.license.StableMatchingApplication.models.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends  CrudRepository<Project, Long>{

}
