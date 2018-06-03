package com.license.smapp.controller;


import com.google.common.reflect.TypeToken;
import com.license.smapp.dto.CourseDto;
import com.license.smapp.exception.BadRequestException;
import com.license.smapp.exception.ResourceNotFoundException;
import com.license.smapp.model.Course;
import com.license.smapp.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
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
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<Course> all = courseService.findAll();
        Type targetListType = new TypeToken<List<CourseDto>>() {}.getType();

        return ResponseEntity.ok(modelMapper.map(all, targetListType));
    }


    /**
     * Find a Course by  id
     *
     * @param id the Id to search by
     *
     * @return An Course Object / message if the object was successfully found or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) throws ResourceNotFoundException {
        Course course = courseService.findById(id);

        if (course == null) {
            throw new ResourceNotFoundException(String.format("Cursul cu id-ul=%s nu a fost gasit!", id));
        }
        return ResponseEntity.ok(modelMapper.map(course, CourseDto.class));
    }

    /**
     * Delete a particular Course object from database
     *
     * @param id the id of the Course to delete
     * @return message if the object was successfully deleted
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) throws ResourceNotFoundException {
        Course course = courseService.findById(id);

        if (course == null) {
            throw new ResourceNotFoundException(String.format("Cursul cu id-ul=%s nu a fost gasit!", id));
        }

        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Add a new Course object into the database
     * @param course the object to be saved
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Course> createUser(@RequestBody CourseDto course) {

        Course newCourse = courseService.save(modelMapper.map(course, Course.class));
        return ResponseEntity.created(URI.create("/courses/" + newCourse.getId())).build();
    }

    /**
     * Update a particular Course Object
     * @param course the object that needs to be updated
     * @param id the id of the object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCourse(@RequestBody CourseDto course, @PathVariable Long id) throws BadRequestException, ResourceNotFoundException {

        if (course.getId() != id) {
            throw new BadRequestException("Id-ul obiectului nu este acelasi cu id-ul din path!");
        }

        Course updatedCourse = this.courseService.findById(id);

        if(updatedCourse == null) {
            throw new ResourceNotFoundException(String.format("Cursul cu id-ul=%s nu a fost gasit!", id));
        }

        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.map(course, updatedCourse);

        courseService.save(updatedCourse);
        return ResponseEntity.ok(updatedCourse);
    }
}
