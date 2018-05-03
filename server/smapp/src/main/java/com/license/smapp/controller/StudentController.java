package com.license.smapp.controller;


import com.license.smapp.dto.AnswerDto;
import com.license.smapp.dto.CreatePreferenceDTO;
import com.license.smapp.dto.CreateStudentDTO;
import com.license.smapp.dto.PreferenceDto;
import com.license.smapp.exception.BadRequestException;
import com.license.smapp.exception.ResourceNotFoundException;
import com.license.smapp.model.*;
import com.license.smapp.service.PreferenceService;
import com.license.smapp.service.ProjectService;
import com.license.smapp.service.QuestionService;
import com.license.smapp.service.StudentService;
import com.license.smapp.service.impl.HibernateSearchServiceImpl;
import javassist.NotFoundException;
import jdk.nashorn.internal.runtime.QuotedStringTokenizer;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.acl.LastOwnerException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/students")
public class StudentController {
    Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private HibernateSearchServiceImpl searchService;

    @Autowired
    private QuestionService questionService;

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


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Student> search(@RequestParam(value = "search", required = false) String searchTerm) {

        List<Student> users = null;

        try {
            users = searchService.fuzzySearchStudents(searchTerm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentByName(@RequestParam("name") String name) throws ResourceNotFoundException {
        Student student = this.studentService.findStudentByName(name);
        if(student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu numele %s nu a fost gasit!", name));
        }

        return ResponseEntity.ok(student);
    }

    @RequestMapping(value = "/{id}/apply", method = RequestMethod.GET)
    public ResponseEntity<?> checkIfStudentCanApplyToProject(@PathVariable Long id,
                                                             @RequestParam("projectId") Long projectId) throws BadRequestException, ResourceNotFoundException {
        Project project = projectService.findById(projectId);
        Student student = studentService.findById(id);

        if (project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", projectId));
        }

        if (student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu id-ul=%s nu a fost gasit!", id));
        }

        Integer count = preferenceService.countAllByProjectAndStudent(project, student);

        if (count != 0) {
            throw new BadRequestException("Nu poti aplica de 2 ori la acelasi proiect!");
        }

        return ResponseEntity.ok(true);
    }


    // TODO: refactor this
    @RequestMapping(value = "/{id}/preferences", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addPreferenceToStudent(@PathVariable Long id,
                                                    @RequestBody CreatePreferenceDTO preferenceDto) throws ResourceNotFoundException, URISyntaxException {

        Student student = studentService.findById(id);

        if (student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu id-ul=%s nu a fost gasit!", id));
        }


        Project project = projectService.findById(preferenceDto.getProjectId());

        if (project == null) {
            throw new ResourceNotFoundException(String.format("Proiectul cu id-ul=%s nu a fost gasit!", preferenceDto.getProjectId()));
        }


        modelMapper.typeMap(AnswerDto.class, Answer.class).addMappings(mp -> {
            mp.<Long>map(src -> src.getQuestionId(), (dest, v) -> dest.getQuestion().setId(v));
        });

        List<Answer> answers = modelMapper.map(preferenceDto.getAnswers(), new TypeToken<List<Answer>>() {}.getType());

        for(Answer answer : answers) {
            Question question = questionService.findById(answer.getQuestion().getId());
            if (question == null) {
                throw new ResourceNotFoundException(String.format("Intrebarea cu id-ul=%s nu a fost gasita!", question.getId()));
            }
            answer.setQuestion(question);
            student.addAnswer(answer);
        }

        student.addPreference(new Preference() {{setProject(project); }});

        studentService.update(student);

        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }


}
