package com.license.smapp.controller;

import com.google.common.reflect.TypeToken;
import com.license.smapp.dto.LecturerDto;
import com.license.smapp.dto.ProjectDto;
import com.license.smapp.exception.BadRequestException;
import com.license.smapp.exception.ResourceNotFoundException;
import com.license.smapp.model.File;
import com.license.smapp.model.Lecturer;
import com.license.smapp.model.Project;
import com.license.smapp.model.Question;
import com.license.smapp.service.LecturerService;
import com.license.smapp.service.ProjectService;
import com.license.smapp.service.StorageService;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/lecturers")
public class LecturerController {

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProjectService projectService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerController.class);

    /**
     * Get a list with all the Lecturer objects from the database
     * @return List with the Lecturer Objects and a message if the request was successfully
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<LecturerDto>> getAllLecturers() {
        List<LecturerDto> all = modelMapper.map(lecturerService.findAll(), new TypeToken<List<LecturerDto>>() {}.getType());
        return ResponseEntity.ok(all);
    }

    /**
     * Find a Lecturer by  id
     *
     * @param id the Id to search by
     * @return An Lecturer Object / message if the object was successfully found or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<LecturerDto> getLecturerById(@PathVariable Long id) throws ResourceNotFoundException {
        Lecturer lecturer = lecturerService.findById(id);

        if (lecturer == null) {
            throw new ResourceNotFoundException(String.format("Profesorul cu id-ul=%s nu a fost gasit!", id));
        }
        return ResponseEntity.ok(modelMapper.map(lecturer, LecturerDto.class));
    }

    /**
     * Delete a particular Lecturer object from database
     *
     * @param id the id of the Lecturer to delete
     * @return message if the object was successfully deleted
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLecturer(@PathVariable Long id) throws ResourceNotFoundException {
        Lecturer lecturer = lecturerService.findById(id);

        if (lecturer == null) {
            throw new ResourceNotFoundException(String.format("Profesorul cu id-ul=%s nu a fost gasit!", id));
        }

        lecturerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Add a new Lecturer object into the database
     * @param lecturer the object to be saved
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Lecturer> createUser(@RequestBody LecturerDto lecturer) throws URISyntaxException {

        Lecturer newLecturer = lecturerService.save(modelMapper.map(lecturer, Lecturer.class));
        return ResponseEntity.created(new URI("/lecturers/" + newLecturer.getId())).build();
    }

    /**
     * Update a particular Lecturer Object
     * @param lecturer the object that needs to be updated
     * @param id the id of the object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLecturer(@RequestBody LecturerDto lecturer, @PathVariable Long id) throws BadRequestException, ResourceNotFoundException {

        if (lecturer.getId() != id) {
            throw new BadRequestException("Id-ul obiectului nu este la fel cu id-ul din path!");
        }

        Lecturer lecturerDb = this.lecturerService.findById(id);

        if (lecturerDb == null) {
            throw new ResourceNotFoundException(String.format("Profesorul cu id-ul=%s nu a fost gasit!", id));
        }
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(lecturer, lecturerDb);
        return ResponseEntity.ok(lecturerService.save(lecturerDb));
    }

    /**
     * Add a project to a particular Lecturer Object
     * @param: id - the id of the Lecturer
     * @param  - the project to add to the lecturer's list
     */
    @RequestMapping(value = "/{id}/projects", method = RequestMethod.POST)
    public ResponseEntity<ProjectDto> addProject(@PathVariable Long id,
                                              @RequestBody ProjectDto projectDto) throws URISyntaxException, ResourceNotFoundException {


        Lecturer lecturer = lecturerService.findById(id);

        if (lecturer == null) {
            throw new ResourceNotFoundException(String.format("Profesorul cu id-ul=%s nu a fost gasit!"));
        }

        Project project = modelMapper.map(projectDto, Project.class);

        project.setLecturer(lecturer);

        Project newProject = this.projectService.save(project);
        ProjectDto newProjectDto = modelMapper.map(newProject, ProjectDto.class);
        return ResponseEntity.created(new URI("/projects/" + newProject.getId())).body(newProjectDto);

    }

    /**
     * Get a List with Project Objects proposed by the Lecturer
     * @param: the id of the Lecturer to search by
     */
    @RequestMapping(value = "{id}/activeProjects", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectDto>> getActiveProjects(@PathVariable Long id) throws ResourceNotFoundException {
        Lecturer lecturer = this.lecturerService.findById(id);

        if (lecturer == null) {
            throw new ResourceNotFoundException(String.format("Profesorul cu id-ul=%s nu a fost gasit!", id));
        }

        List<Project> projects = this.projectService.getProjectsForLecturer(lecturer, true);

        return ResponseEntity.ok(modelMapper.map(projects,  new TypeToken<List<ProjectDto>>() {}.getType()));
    }

    @RequestMapping(value = "{id}/inactiveProjects", method = RequestMethod.GET)
    public ResponseEntity<?> getInactiveProjects(@PathVariable Long id) throws ResourceNotFoundException {
        Lecturer lecturer = this.lecturerService.findById(id);

        if (lecturer == null) {
            throw new ResourceNotFoundException(String.format("Profesorul cu id-ul=%s nu a fost gasit!", id));
        }

        List<Project> projects = this.projectService.getProjectsForLecturer(lecturer, false);

        return ResponseEntity.ok(modelMapper.map(projects, new TypeToken<List<ProjectDto>>() {}.getType()));
    }

    @RequestMapping(value = "{id}/projects", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProjects(@PathVariable Long id,
                                            @RequestBody List<ProjectDto> projects) throws ResourceNotFoundException, BadRequestException {
        Lecturer lecturer = this.lecturerService.findById(id);

        if (lecturer == null) {
            throw new ResourceNotFoundException(String.format("Profesorul cu id-ul=%s nu a fost gasit!", id));
        }

        modelMapper.getConfiguration().setSkipNullEnabled(true);

        for (ProjectDto project : projects) {
            Project dbProject = projectService.findById(project.getId());

            if (dbProject == null) {
                throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", id));
            }

            if (dbProject.getLecturer() != lecturer) {
                throw new BadRequestException(String.format("Nu poti edita un proiect care nu iti apartine %s", dbProject.getId()));
            }

            modelMapper.map(project, dbProject);

            projectService.save(dbProject);
        }
        return ResponseEntity.noContent().build();
    }
}
