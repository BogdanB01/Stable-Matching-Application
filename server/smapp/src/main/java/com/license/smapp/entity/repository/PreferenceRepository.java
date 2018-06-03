package com.license.smapp.entity.repository;

import com.license.smapp.entity.model.Preference;
import com.license.smapp.entity.model.Project;
import com.license.smapp.entity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Integer countAllByProjectAndStudent(Project project, Student student);
    List<Preference> findAllByProject(Project project);
}