package com.license.StableMatchingApplication.repository;

import com.license.StableMatchingApplication.models.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long>{

}
