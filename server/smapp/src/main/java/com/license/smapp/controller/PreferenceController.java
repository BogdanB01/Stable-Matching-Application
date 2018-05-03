package com.license.smapp.controller;

import com.license.smapp.dto.PreferenceDto;
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
    public ResponseEntity<?> addPreferenceToStudent(@RequestBody PreferenceDto preferenceDto) throws ResourceNotFoundException, URISyntaxException {

        Student student = studentService.findById(preferenceDto.getStudentId());

        if (student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu id-ul=%s nu a fost gasit!", preferenceDto.getStudentId()));
        }

        Project project = projectService.findById(preferenceDto.getProjectId());

        if (project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", preferenceDto.getProjectId()));
        }

        modelMapper.typeMap(PreferenceDto.class, Preference.class).addMappings(mp -> {
            mp.<Long>map(src -> src.getProjectId(), (dest, v) -> dest.getProject().setId(v));
            mp.<Long>map(src -> src.getStudentId(), (dest, v) -> dest.getStudent().setId(v));
        });

        Preference preference = modelMapper.map(preferenceDto, Preference.class);

        // set submission date
        preference.setSubmittedAt(new Date());

        Preference newPreference = preferenceService.save(preference);
        return ResponseEntity.created(new URI("/preferences/" + newPreference.getId())).build();
    }

}
