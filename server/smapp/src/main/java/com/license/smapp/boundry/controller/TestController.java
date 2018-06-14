package com.license.smapp.boundry.controller;

import com.license.smapp.aop.StopMethodExecution;
import com.license.smapp.common.PdfGenerator;
import com.license.smapp.entity.model.Project;
import com.license.smapp.entity.model.Student;
import com.license.smapp.control.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping( value = "/api")
public class TestController {

    @RequestMapping(method = RequestMethod.GET, value = "/test/student")
    @PreAuthorize("hasRole('STUDENT')")
    public String test() {
        return "STUDENTULE";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/test/profesor")
    @PreAuthorize("hasRole('LECTURER')")
    public String testLect() {
        return "PROFESORULE";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/admin")
//    @PreAuthorize("hasRole('ADMIN')")
    @StopMethodExecution
    public String testAdmin() {
        return "PROFESORULE";
    }


    @Autowired
    MatchingService matchingService;

    @RequestMapping(value = "/report", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> testAlgorithm() throws IOException {
        Map<Student, Project> matches = matchingService.match();

        ByteArrayInputStream bis = PdfGenerator.matchesReport(matches);

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=matchesReport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}

