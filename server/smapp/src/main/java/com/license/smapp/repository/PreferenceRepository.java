package com.license.smapp.repository;

import com.license.smapp.model.Preference;
import com.license.smapp.model.Project;
import com.license.smapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Integer countAllByProjectAndStudent(Project project, Student student);
}