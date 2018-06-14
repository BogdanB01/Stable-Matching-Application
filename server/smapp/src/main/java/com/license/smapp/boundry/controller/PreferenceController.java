package com.license.smapp.boundry.controller;

import com.license.smapp.boundry.dto.PreferenceDto;
import com.license.smapp.boundry.exceptions.BadRequestException;
import com.license.smapp.boundry.exceptions.ResourceNotFoundException;
import com.license.smapp.entity.model.Project;
import com.license.smapp.entity.model.Student;
import com.license.smapp.control.service.PreferenceService;
import com.license.smapp.control.service.ProjectService;
import com.license.smapp.control.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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


    @PreAuthorize("hasRole('STUDENT')")
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
