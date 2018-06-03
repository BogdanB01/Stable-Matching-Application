package com.license.smapp.entity.repository;

import com.license.smapp.entity.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    Course findByAbbreviation(String abbreviation);
}
