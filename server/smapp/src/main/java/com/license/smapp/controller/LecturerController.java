package com.license.smapp.controller;

import com.license.smapp.dto.CreateLecturerDTO;
import com.license.smapp.dto.CreateProjectDTO;
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
    private StorageService storageService;

    @Autowired
    private ProjectService projectService;

    private Logger LOGGER = LoggerFactory.getLogger(LecturerController.class);

    /**
     * Get a list with all the Lecturer objects from the database
     * @return List with the Lecturer Objects and a message if the request was successfully
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Lecturer>> getAllLecturers() {
        List<Lecturer> all = lecturerService.findAll();
        return ResponseEntity.ok(all);
    }

    /**
     * Find a Lecturer by  id
     *
     * @param id the Id to search by
     * @return An Lecturer Object / message if the object was successfully found or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Lecturer> getLecturerById(@PathVariable Long id) {
        Lecturer lecturer = lecturerService.findById(id);
        if (lecturer != null) {
            return ResponseEntity.ok(lecturer); // return 200, with json body
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 with null body
        }
    }

    /**
     * Delete a particular Lecturer object from database
     *
     * @param id the id of the Lecturer to delete
     * @return message if the object was successfully deleted
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Lecturer> deleteLecturerById(@PathVariable Long id) {
        Lecturer lecturer = lecturerService.findById(id);
        if(lecturer == null) {
            return ResponseEntity.notFound().build();
        }

        lecturerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Add a new Lecturer object into the database
     * @param lecturer the object to be saved
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Lecturer> createUser(@RequestBody @Valid CreateLecturerDTO lecturer) throws URISyntaxException {

        Lecturer newLecturer = lecturerService.save(modelMapper.map(lecturer, Lecturer.class));
        return ResponseEntity.created(new URI("/lecturers/" + newLecturer.getId())).build();
    }

    /**
     * Update a particular Student Object
     * @param lecturer the object that needs to be updated
     * @param id the id of the object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateLecturer(@RequestBody Lecturer lecturer, @PathVariable Long id) {
        Lecturer updatedLecturer = this.lecturerService.findById(id);
        if(updatedLecturer == null) {
            return ResponseEntity.notFound().build();
        }
        lecturerService.save(updatedLecturer);
        return ResponseEntity.noContent().build();
    }

    /**
     * Add a project to a particular Lecturer Object
     * @param: id - the id of the Lecturer
     * @param  - the project to add to the lecturer's list
     */
    @RequestMapping(value = "/{id}/projects", method = RequestMethod.POST)
    public ResponseEntity<Project> addProject(@PathVariable Long id,
                                              @RequestBody CreateProjectDTO projectDTO) throws URISyntaxException, ResourceNotFoundException {


        Lecturer lecturer = lecturerService.findById(id);

        if (lecturer == null) {
            throw new ResourceNotFoundException(String.format("Profesorul cu id-ul=%s nu a fost gasit!"));
        }

        Project project = modelMapper.map(projectDTO, Project.class);
        project.setLecturer(lecturer);

        Project newProject = this.projectService.save(project);
        return ResponseEntity.created(new URI("/projects/" + newProject.getId())).body(newProject);

    }

    /**
     * Get a List with Project Objects proposed by the Lecturer
     * @param: the id of the Lecturer to search by
     */
    @RequestMapping(value = "{id}/projects", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getProjects(@PathVariable Long id) {
        Lecturer lecturer = this.lecturerService.findById(id);
        List<Project> projects = lecturer.getProjects();
        return ResponseEntity.ok(projects);
    }
}
