package com.license.smapp.boundry.controller;

import com.license.smapp.common.PdfGenerator;
import com.license.smapp.entity.model.History;
import com.license.smapp.control.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    private Logger LOGGER = LoggerFactory.getLogger(HistoryController.class);

    @RequestMapping(value = "/years", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> getAllDistinctYears() {
       return ResponseEntity.ok(historyService.getDistinctYears());
    }

    @RequestMapping(value = "/{year}", method = RequestMethod.GET)
    public ResponseEntity<?> getReportForYear(@PathVariable Integer year) {
        LOGGER.error("SALUT");
        List<History> reportForYear = historyService.getReportForYear(year);
        ByteArrayInputStream bis = PdfGenerator.getReportForSpecificYear(reportForYear);

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + year + ".pdf\"");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}
