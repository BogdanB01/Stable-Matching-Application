package com.license.smapp.entity.repository;

import com.license.smapp.entity.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("select distinct year from History order by year")
    List<Integer> getDistinctYears();

    List<History> findAllByYear(Integer year);
}
