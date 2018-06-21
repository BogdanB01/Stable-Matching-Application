package com.license.smapp.entity.repository;

import com.license.smapp.entity.model.Student;
import com.license.smapp.entity.repository.custom.StudentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom {
    Student findByName(String name);
    List<Student> findTop10ByNameIsContaining(String searchTerm);
    Student findByRegistrationNumber(String registrationNumber);
    void deleteAll();
}
