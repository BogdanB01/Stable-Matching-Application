package com.license.smapp.controller;


import com.license.smapp.dto.CreateCourseDTO;
import com.license.smapp.model.Course;
import com.license.smapp.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get a list with all the Course objects from the database
     * @return List with the Course Objects and a message if the request was successfully
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> all = courseService.findAll();
        return ResponseEntity.ok(all);
    }


    /**
     * Find a Course by  id
     *
     * @param id the Id to search by
     *
     * @return An Course Object / message if the object was successfully found or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.findById(id);
        if (course != null) {
            return ResponseEntity.ok(course); // return 200, with json body
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 with null body
        }
    }

    /**
     * Delete a particular Course object from database
     *
     * @param id the id of the Course to delete
     * @return message if the object was successfully deleted
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Course> deleteUserById(@PathVariable Long id) {
        Course course = courseService.findById(id);
        if(course == null) {
            return ResponseEntity.notFound().build();
        }

        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Add a new Course object into the database
     * @param course the object to be saved
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Course> createUser(@RequestBody @Valid CreateCourseDTO course) throws URISyntaxException {

        Course newCourse = courseService.save(modelMapper.map(course, Course.class));
        return ResponseEntity.created(new URI("/users/" + newCourse.getId())).build();

        //if resource already exists -> return ResponseEntity.status(HTTPStatus.Conflict).build();
    }
}
