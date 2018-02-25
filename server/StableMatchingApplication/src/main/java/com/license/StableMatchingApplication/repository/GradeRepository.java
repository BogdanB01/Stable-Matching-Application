package com.license.StableMatchingApplication.repository;

import com.license.StableMatchingApplication.models.Grade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Long>{

}
