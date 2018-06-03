package com.license.smapp.repository;

import com.license.smapp.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("select distinct year from History")
    List<Integer> getDistinctYears();

    List<History> findAllByYear(Integer year);
}
