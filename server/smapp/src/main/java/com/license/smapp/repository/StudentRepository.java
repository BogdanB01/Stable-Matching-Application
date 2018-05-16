package com.license.smapp.repository;

import com.license.smapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    Student findByName(String name);
    List<Student> findTop10ByNameIsContaining(String searchTerm);
}
