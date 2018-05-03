package com.license.smapp.controller;

import com.license.smapp.dto.GetProjectDTO;
import com.license.smapp.dto.UpdateProjectDTO;
import com.license.smapp.exception.BadRequestException;
import com.license.smapp.exception.ResourceNotFoundException;
import com.license.smapp.model.*;
import com.license.smapp.service.PreferenceService;
import com.license.smapp.service.ProjectService;
import com.license.smapp.service.StudentService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get a list with all the Project objects from the database
     * @return List with the Project Objects and a message if the request was successfully
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<GetProjectDTO>> getAllProjects() {
        List<Project> all = projectService.findAll();

        java.lang.reflect.Type targetListType = new TypeToken<List<GetProjectDTO>>() {}.getType();
        List<GetProjectDTO> allProjects = modelMapper.map(all, targetListType);

        return  ResponseEntity.ok(allProjects);
    }

    /**
     * Get a paginated list of Projects from database
     * @param page Results page you want to retrieve
     * @param size Number of records per page
     * @return A Page object with the requested items
     */

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Page<Project>> list(@RequestParam(value="page") int page,
                                              @RequestParam(value="size") int size) {
        Page<Project> projects = projectService.listAllByPage(new PageRequest(page, size));

        return ResponseEntity.ok(projects);
    }



    /**
     * Find a Project by id
     *
     * @param id the Id to search by
     * @return An Project Object / message if the object was successfully found or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) throws ResourceNotFoundException {
        Project project = projectService.findById(id);

        if (project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", id));
        }
        return ResponseEntity.ok(project); // return 200, with json body
    }

    /**
     * Delete a particular Project object from database
     *
     * @param id the id of the Project to delete
     * @return message if the object was successfully deleted
     */
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
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Project> updateProject(@RequestBody UpdateProjectDTO projectDTO, @PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
        // validate request
        if (!projectDTO.getId().equals(id)) {
            throw new BadRequestException(String.format("Id-ul obiectului %s nu este acelasi cu id-ul primit ca parametru %s", projectDTO.getId(), id));
        }

        Project project = this.projectService.findById(id);

        if (project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", id));
        }

        Project updatedProject = projectService.update(project, modelMapper.map(projectDTO, Project.class));

        return ResponseEntity.ok(updatedProject);
  //      return null;
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.PATCH)
    public ResponseEntity<Project> unassignStudentFromProject(@PathVariable Long id, @RequestBody Student student) throws BadRequestException, ResourceNotFoundException {
        Student studentDb = studentService.findById(student.getId());
        Project projectDb = projectService.findById(id);

        if(studentDb == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu numele %s nu a fost gasit!", student.getName()));
        }

        if(projectDb == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul %s nu a fost gasit!", id));
        }

        if(projectDb.getAssignedProjects().stream()
                .filter(s -> s.getStudent().getId().equals(student.getId())).collect(Collectors.toList()).size() == 0) {
            throw new BadRequestException(String.format("Studentul cu id-ul %s nu este asignat la acest proiect", student.getId()));
        }

        AssignedProjects assignedProjects = projectDb.getAssignedProjects().stream()
                .filter(a -> a.getStudent().getId().equals(studentDb.getId())).findFirst().get();

        projectDb.unnasignStudent(assignedProjects);
        Project updatedProject = projectService.save(projectDb);

        return ResponseEntity.ok(updatedProject);
    }


    @RequestMapping(value = "/{id}/assign", method = RequestMethod.PATCH)
    public ResponseEntity<Project> assignStudentToProject(@PathVariable Long id, @RequestBody Student student) throws BadRequestException, ResourceNotFoundException {
        Student studentDb = studentService.findStudentByName(student.getName());
        Project projectDb = projectService.findById(id);

        if(studentDb == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu numele %s nu a fost gasit!", student.getName()));
        }

        if(projectDb == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul %s nu a fost gasit!", id));
        }

        if(projectDb.getAssignedProjects().size() >= projectDb.getCapacity()) {
            throw new BadRequestException(String.format("Nu mai poti adauga studenti la acest proiect!"));
        }

        if (projectDb.getAssignedProjects().stream()
                .filter(c -> c.getStudent().getId().equals(studentDb.getId())).collect(Collectors.toList()).size() > 0) {
            throw new BadRequestException(String.format("Nu poti adauga de doua ori acelasi student!"));
        }

        projectDb.getAssignedProjects().add(new AssignedProjects() {{ setStudent(studentDb); setProject(projectDb);}});

        Project updatedProject = projectService.save(projectDb);

        return ResponseEntity.ok(updatedProject);
    }

}
