package com.license.smapp.controller;

import com.license.smapp.dto.PreferenceDto;
import com.license.smapp.exception.BadRequestException;
import com.license.smapp.exception.ResourceNotFoundException;
import com.license.smapp.model.Preference;
import com.license.smapp.model.Project;
import com.license.smapp.model.Student;
import com.license.smapp.service.PreferenceService;
import com.license.smapp.service.ProjectService;
import com.license.smapp.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.StubNotFoundException;
import java.util.Date;

@RestController
@RequestMapping(value = "/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createPreference(@RequestBody PreferenceDto preference) throws ResourceNotFoundException, BadRequestException {
        Student student = studentService.findById(preference.getStudent().getId());
        Project project = projectService.findById(preference.getProject().getId());

        if (student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu id-ul=%s nu a fost gasit!", preference.getStudent().getId()));
        }

        if (project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", preference.getProject().getId()));
        }

        Integer count = preferenceService.countAllByProjectAndStudent(project, student);

        if (count != 0) {
            throw new BadRequestException("Nu poti aplica de doua ori la acelasi proiect!");
        }

        return null;
    }


}
