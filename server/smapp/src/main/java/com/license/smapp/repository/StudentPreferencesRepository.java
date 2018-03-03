package com.license.smapp.repository;

import com.license.smapp.model.StudentPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPreferencesRepository extends JpaRepository<StudentPreferences, Long> {
}