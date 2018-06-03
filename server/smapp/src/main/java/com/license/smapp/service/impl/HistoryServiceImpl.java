package com.license.smapp.service.impl;

import com.license.smapp.model.History;
import com.license.smapp.repository.HistoryRepository;
import com.license.smapp.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService{

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<Integer> getDistinctYears() {
        return historyRepository.getDistinctYears();
    }

    @Override
    public List<History> getReportForYear(Integer year) {
        return historyRepository.findAllByYear(year);
    }
}
