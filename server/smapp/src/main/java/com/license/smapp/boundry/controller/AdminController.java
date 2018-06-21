package com.license.smapp.boundry.controller;

import com.license.smapp.boundry.exceptions.BadRequestException;
import com.license.smapp.common.PdfGenerator;
import com.license.smapp.common.TimeProvider;
import com.license.smapp.control.service.CourseService;
import com.license.smapp.control.service.MatchingService;
import com.license.smapp.control.service.StudentService;
import com.license.smapp.entity.model.*;
import com.license.smapp.control.service.HistoryService;
import com.license.smapp.entity.repository.ApplicationStateRepository;
import com.license.smapp.entity.repository.CourseRepository;
import com.license.smapp.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    TimeProvider timeProvider;

    @Autowired
    private ApplicationStateRepository applicationStateRepository;

    private Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value = "/years", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> getAllDistinctYears() {
       return ResponseEntity.ok(historyService.getDistinctYears());
    }

    @RequestMapping(value = "/{year}", method = RequestMethod.GET)
    public ResponseEntity<?> getReportForYear(@PathVariable Integer year) {
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

    @RequestMapping(value = "/report", method = RequestMethod.GET, produces = {MediaType.APPLICATION_PDF_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InputStreamResource> studentProjectAllocation() throws IOException, BadRequestException {

        Integer currentYear = timeProvider.now().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();

        if (historyService.getDistinctYears().contains(currentYear)) {
            throw new BadRequestException("Proiectele pentru acest an sunt deja repartizate!");
        }

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

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public ResponseEntity<?> saveMatchesToDatabase() throws BadRequestException {

        Integer currentYear = timeProvider.now().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();

        if (historyService.getDistinctYears().contains(currentYear)) {
            throw new BadRequestException("Proiectele pentru acest an sunt deja repartizate!");
        }

        Map<Student, Project> matches = matchingService.match();
        matchingService.saveSolution(matches);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/app/state", method = RequestMethod.PUT)
    public ResponseEntity<?> updateState(@RequestBody Map<String, Object> body) throws BadRequestException {

        State state;
        try {
            state = State.valueOf(body.get("status").toString());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Status aplicatie necunoscut!");
        }

        ApplicationState appState = applicationStateRepository.findOne(Constants.STATE_ID);
        if (appState == null) {
            return ResponseEntity.unprocessableEntity().build();
        }

        appState.setState(state);
        applicationStateRepository.save(appState);
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "/app/state", method = RequestMethod.GET)
    public ResponseEntity<?> getApplicationState() {
        return ResponseEntity.ok(applicationStateRepository.findOne(Constants.STATE_ID));
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public ResponseEntity<?> prepareNewSession() {
        this.studentService.deleteAll();
        this.courseService.deleteAll();

        return null;
    }
}
