package com.license.smapp.controller;

import com.license.smapp.model.Project;
import com.license.smapp.service.ProjectService;
import jdk.nashorn.internal.ir.PropertyKey;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Get a list with all the Project objects from the database
     * @return List with the Project Objects and a message if the request was successfully
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> all = projectService.findAll();
        return ResponseEntity.ok(all);
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
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        if (project != null) {
            return ResponseEntity.ok(project); // return 200, with json body
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 with null body
        }
    }

    /**
     * Delete a particular Project object from database
     *
     * @param id the id of the Project to delete
     * @return message if the object was successfully deleted
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Project> deleteProjectById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        if(project == null) {
            return ResponseEntity.notFound().build();
        }

        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
