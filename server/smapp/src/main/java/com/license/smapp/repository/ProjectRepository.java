package com.license.smapp.repository;

import com.license.smapp.model.Lecturer;
import com.license.smapp.model.Project;
import com.license.smapp.repository.custom.ProjectRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {
    Page<Project> findAllByLecturerNameContainingOrTitleContaining(String lecturer, String title, Pageable pageable);
    List<Project> findAllByLecturerAndActive(Lecturer lecturer, boolean active);
    Page<Project> findAllByActive(boolean active, Pageable pageable);
    Page<Project> findAllByActiveAndTitleStartingWithIgnoreCase(boolean active, String filter, Pageable pageable);
    Page<Project> findAllByActiveAndLecturerNameStartingWithIgnoreCase(boolean active, String filter, Pageable pageable);
    Page<Project> findDistinctByActiveAndTags_NameStartingWithIgnoreCase(boolean active, String filter, Pageable pageable);
}
