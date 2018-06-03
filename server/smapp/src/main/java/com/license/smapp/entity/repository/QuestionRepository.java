package com.license.smapp.entity.repository;

import com.license.smapp.entity.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
