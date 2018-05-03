package com.license.smapp.repository;

import com.license.smapp.model.Project;
import com.license.smapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
