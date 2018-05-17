package com.license.smapp.repository;

import com.license.smapp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    Course findByAbbreviation(String abbreviation);
}
