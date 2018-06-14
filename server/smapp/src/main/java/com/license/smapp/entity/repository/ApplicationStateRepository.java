package com.license.smapp.entity.repository;

import com.license.smapp.entity.model.ApplicationState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationStateRepository extends JpaRepository<ApplicationState, Long> {
}
