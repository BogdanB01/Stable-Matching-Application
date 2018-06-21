package com.license.smapp.boundry.controller;

import com.license.smapp.aop.StopMethodExecution;
import com.license.smapp.boundry.dto.*;
import com.license.smapp.boundry.exceptions.BadRequestException;
import com.license.smapp.boundry.exceptions.ResourceNotFoundException;
import com.license.smapp.entity.model.*;
import com.license.smapp.control.service.PreferenceService;
import com.license.smapp.control.service.ProjectService;
import com.license.smapp.control.service.StudentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get a list with all the Project objects from the database
     * @return List with the all the projects from the database
     */
    @PreAuthorize("hasRole('LECTURER') or hasRole('ADMIN') or hasRole('STUDENT')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<Project> all = projectService.findAll();

        java.lang.reflect.Type targetListType = new TypeToken<List<GetProjectDTO>>() {}.getType();
        List<ProjectDto> allProjects = modelMapper.map(all, targetListType);

        return  ResponseEntity.ok(allProjects);
    }

    /**
     * Get a paginated list of Projects from database
     * @return A Page object with the requested items
     */
    @PreAuthorize("hasRole('LECTURER') or hasRole('ADMIN') or hasRole('STUDENT')")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Page<?>> listProjects(@RequestParam(value = "type", required = false, defaultValue = "all") String type,
                                                         @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                                                         Pageable pageable) throws BadRequestException {

        Type targetListType = new TypeToken<List<ProjectDto>>() {}.getType();
        switch (type) {
            case "all":
                Page<Project> page = projectService.listAllProjectsByActive(true, pageable);
                return ResponseEntity.ok(new PageImpl<>(modelMapper.map(page.getContent(), targetListType), pageable, page.getTotalElements()));
            case "title":
                Page<Project> filteredProjectsByTitle = projectService.filterActiveProjectsByProjectTitle(true, filter, pageable);
                return ResponseEntity.ok(new PageImpl<>(modelMapper.map(filteredProjectsByTitle.getContent(), targetListType), pageable, filteredProjectsByTitle.getTotalElements()));
            case "lecturer":
                Page<Project> filteredProjectsByLecturerName = projectService.filterActiveProjectsByLecturerName(true, filter, pageable);
                return ResponseEntity.ok(new PageImpl<>(modelMapper.map(filteredProjectsByLecturerName.getContent(), targetListType), pageable, filteredProjectsByLecturerName.getTotalElements()));
            case "tag":
                Page<Project> filteredProjectsByTagName = projectService.filterActiveProjectsByTagName(true, filter, pageable);
                return ResponseEntity.ok(new PageImpl<>(modelMapper.map(filteredProjectsByTagName.getContent(), targetListType), pageable, filteredProjectsByTagName.getTotalElements()));
            default:
                throw new BadRequestException("Tip necunoscut");
        }
    }


    @PreAuthorize("hasRole('LECTURER') or hasRole('ADMIN') or hasRole('STUDENT')")

    /**
     * Find a Project by id
     * @param id the Id to search by
     * @return An Project Object / message if the object was successfully found or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) throws ResourceNotFoundException {
        Project project = projectService.findById(id);

        if (project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", id));
        }
        return ResponseEntity.ok(modelMapper.map(project, ProjectDto.class)); // return 200, with json body
    }

    /**
     * Delete a particular Project object from database
     *
     * @param id the id of the Project to delete
     * @return message if the object was successfully deleted
     */
    @PreAuthorize("hasRole('LECTURER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Project> deleteProjectById(@PathVariable Long id) throws ResourceNotFoundException {
        Project project = projectService.findById(id);
        if(project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", id));
        }

        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update a particular Project Object
     * @param projectDTO the object that needs to be updated
     * @param id the id of the object
     */
    @PreAuthorize("hasRole('LECTURER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ProjectDto> updateProject(@RequestBody UpdateProjectDTO projectDTO, @PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
        // validate request
        if (!projectDTO.getId().equals(id)) {
            throw new BadRequestException(String.format("Id-ul obiectului %s nu este acelasi cu id-ul primit ca parametru %s", projectDTO.getId(), id));
        }

        Project project = this.projectService.findById(id);

        if (project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", id));
        }

        Project updatedProject = projectService.update(project, modelMapper.map(projectDTO, Project.class));

        return ResponseEntity.ok(modelMapper.map(updatedProject, ProjectDto.class));
    }

    @PreAuthorize("hasRole('LECTURER')")
    @RequestMapping(value = "/{id}/preferences", method = RequestMethod.GET)
    public ResponseEntity<List<PreferenceDto>> getPreferencesForProject(@PathVariable Long id) throws ResourceNotFoundException {
        Project project = projectService.findById(id);

        if (project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!"));
        }

        Type targetListType = new TypeToken<List<PreferenceDto>>() {}.getType();
        List<Preference> preferences = new ArrayList<>(project.getPreferences());
        return ResponseEntity.ok(modelMapper.map(preferences, targetListType));
    }

    @PreAuthorize("hasRole('LECTURER')")
    @StopMethodExecution
    @RequestMapping(value = "/{id}/preferences", method = RequestMethod.PUT)
    public ResponseEntity<?> reorderPreferencesForProject(@PathVariable Long id,
                                                          @RequestBody List<PreferenceDto> preferences) throws ResourceNotFoundException, BadRequestException {
        Project project = projectService.findById(id);

        if (project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", id));
        }

        SortedSet<Preference> preferencesDb = project.getPreferences();

        for (Preference p : preferencesDb) {
            PreferenceDto preference = preferences.stream().filter(pref -> pref.getId().equals(p.getId())).findFirst().orElse(null);

            if (preference == null) {
                throw new BadRequestException(String.format("Preferinta cu id-ul=%s nu se afla in lista de preferinte a proiectului", preference.getId()));
            }

            p.setPos(preferences.indexOf(preference));
        }

        projectService.save(project);

        return ResponseEntity.noContent().build();
    }
}
