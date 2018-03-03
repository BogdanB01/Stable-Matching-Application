package com.license.smapp.controller;


import com.license.smapp.dto.CreateStudentDTO;
import com.license.smapp.model.Grade;
import com.license.smapp.model.Student;
import com.license.smapp.service.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/students")
public class StudentController {
    Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get a list with all the Student objects from the database
     * @return List with the Student Objects and a message if the request was successfully
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> all = studentService.findAll();
        return ResponseEntity.ok(all);
    }

    /**
     * Find a Student by id
     *
     * @param id the Id to search by
     *
     * @return An Student Object / message if the object was successfully found or not
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(student); //return 200 with json body
    }

    /**
     * Delete a particular Student object from database
     *
     * @param id the id of the User to delete
     * @return message if the object was successfully deleted
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Student> deleteStudentById(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Add a new Student object into the database
     * @param student the object to be saved
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Student> createStudent(@RequestBody @Valid CreateStudentDTO student) throws URISyntaxException {

        Student savedStudent = studentService.save(modelMapper.map(student, Student.class));
        return ResponseEntity.created(new URI("/students/" + savedStudent.getId())).build();

    }

    /**
     * Update a particular Student Object
     * @param student the object that needs to be updated
     * @param id the id of the object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateStudent(@RequestBody Student student, @PathVariable Long id) {
        Student updatedStudent = this.studentService.findById(id);
        if(updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }

        studentService.save(updatedStudent);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a list with grades for a Student Object
     * @param id the id of the student
     * @return a list of that particular student grades
     */
    @RequestMapping(value = "/{id}/grades", method = RequestMethod.GET)
    public ResponseEntity<List<Grade>> getGrades(@PathVariable Long id) {
        List<Grade> grades = this.studentService.getGradesForStudent(id);
        return ResponseEntity.ok(grades);
    }



}
