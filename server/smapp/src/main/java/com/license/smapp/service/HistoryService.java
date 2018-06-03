package com.license.smapp.service;

import com.license.smapp.model.History;

import java.util.List;

public interface HistoryService {
    List<Integer> getDistinctYears();
    List<History> getReportForYear(Integer year);
}
