package com.license.smapp.control.service;

import com.license.smapp.entity.model.History;

import java.util.List;

public interface HistoryService {
    List<Integer> getDistinctYears();
    List<History> getReportForYear(Integer year);
}
