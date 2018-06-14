package com.license.smapp.boundry.controller;


import com.license.smapp.boundry.dto.*;
import com.license.smapp.boundry.exceptions.BadRequestException;
import com.license.smapp.boundry.exceptions.ResourceNotFoundException;
import com.license.smapp.entity.model.*;
import com.license.smapp.control.service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

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
    private QuestionService questionService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get a list with all the Student objects from the database
     * @return List with the Student Objects and a message if the request was successfully
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<Student> students = studentService.findAll();
        Type targetListType = new TypeToken<List<StudentDto>>() {}.getType();
        return ResponseEntity.ok(modelMapper.map(students, targetListType));
    }

    /**
     * Find a Student by id
     *
     * @param id the Id to search by
     *
     * @return An Student Object / message if the object was successfully found or not
     */
    @PreAuthorize("hasRole('LECTURER') or hasRole('ADMIN') or hasRole('STUDENT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(modelMapper.map(student, StudentDto.class)); //return 200 with json body
    }

    /**
     * Delete a particular Student object from database
     *
     * @param id the id of the User to delete
     * @return message if the object was successfully deleted
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Student> deleteStudentById(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Add a new Student object into the database
     * @param student the object to be saved
     */
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateStudent(@RequestBody Student student, @PathVariable Long id) throws ResourceNotFoundException {
        Student updatedStudent = this.studentService.findById(id);
        if(updatedStudent == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu id-ul=%s nu a fost gasit!", id));
        }

        studentService.save(updatedStudent);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a list with grades for a Student Object
     * @param id the id of the student
     * @return a list of that particular student grades
     */
    @PreAuthorize("hasRole('LECTURER') or hasRole('STUDENT') or hasRole('ADMIN')")
    @RequestMapping(value = "/{id}/grades", method = RequestMethod.GET)
    public ResponseEntity<List<Grade>> getGrades(@PathVariable Long id) {
        List<Grade> grades = this.studentService.getGradesForStudent(id);
        return ResponseEntity.ok(grades);
    }


    @PreAuthorize("hasRole('LECTURER') or hasRole('ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<StudentDto>> search(@RequestParam(value = "search", required = false) String searchTerm) {

        List<Student> students = studentService.searchStudent(searchTerm);
        Type targetListType = new TypeToken<List<StudentDto>>() {}.getType();

        return ResponseEntity.ok(modelMapper.map(students, targetListType));
    }

    @PreAuthorize("hasRole('LECTURER') or hasRole('ADMIN')")
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentByName(@RequestParam("name") String name) throws ResourceNotFoundException {
        Student student = this.studentService.findStudentByName(name);
        if(student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu numele %s nu a fost gasit!", name));
        }

        return ResponseEntity.ok(student);
    }

    @PreAuthorize("hasRole('STUDENT')")
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
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }


    @PreAuthorize("hasRole('LECTURER') or hasRole('ADMIN')")
    @RequestMapping(value = "/{id}/details", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentDetails(@PathVariable Long id,
                                               @RequestParam("projectId") Long projectId) throws ResourceNotFoundException {

        Student student = studentService.findById(id);

        if (student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu id-ul=%s nu a fost gasit!"));
        }

        StudentDetailsDto studentDetails = new StudentDetailsDto();
        // notele studentului
        studentDetails.setGrades(modelMapper.map(student.getGrades(), new TypeToken<List<GradeDto>>() {}.getType()));

        // raspunsurile la intrebarile proiectului
        List<Answer> answers = student.getAnswers().stream().filter(a -> a.getQuestion().getProject().getId().equals(projectId)).collect(Collectors.toList());
        studentDetails.setAnswers(modelMapper.map(answers, new TypeToken<List<FullAnswerDto>>() {}.getType()));
        return ResponseEntity.ok(studentDetails);
    }

    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    @RequestMapping(value = "/{id}/project", method = RequestMethod.GET)
    public ResponseEntity<?> getAssignedProjectForStudent(@PathVariable Long id) throws ResourceNotFoundException {
        Student student = studentService.findById(id);

        if (student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu id-ul=%s nu a fost gasit!", id));
        }
        if (student.getProject() == null) {
            return ResponseEntity.ok(null);
        }
        ProjectDto projectDto = modelMapper.map(student.getProject(), ProjectDto.class);
        return ResponseEntity.ok(projectDto);
    }

    // TODO: refactor this
    @PreAuthorize("hasRole('STUDENT')")
    @RequestMapping(value = "/{id}/preferences", method = RequestMethod.POST)
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

        LOGGER.error(student.getAvg().toString());

        List<Answer> answers = modelMapper.map(preferenceDto.getAnswers(), new TypeToken<List<Answer>>() {}.getType());

        for(Answer answer : answers) {
            Question question = questionService.findById(answer.getQuestion().getId());
            if (question == null) {
                throw new ResourceNotFoundException(String.format("Intrebarea cu id-ul=%s nu a fost gasita!", question.getId()));
            }
            answer.setQuestion(question);
            student.addAnswer(answer);
        }

        student.addPreference(new Preference() {{setProject(project); setPersonalNote(preferenceDto.getPersonalNote());  }});

        studentService.update(student);

      return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }


    @PreAuthorize("hasRole('STUDENT')")
    @RequestMapping(value = "/{id}/preferences", method = RequestMethod.GET)
    public ResponseEntity<?> getPreferencesForStudent(@PathVariable Long id) throws ResourceNotFoundException {

        Student student = studentService.findById(id);

        if (student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu id-ul=%s nu a fost gasit!", id));
        }
        List<Preference> dbPreferences = student.getPreferences().stream().filter(a -> a != null).collect(Collectors.toList());
        List<PreferenceDto> preferences = modelMapper.map(dbPreferences, new TypeToken<List<PreferenceDto>>() {}.getType());
        return ResponseEntity.ok(preferences);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @RequestMapping(value = "/{id}/preferences/{preferenceId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePreferenceFromStudent(@PathVariable Long id,
                                                         @PathVariable Long preferenceId) throws ResourceNotFoundException, BadRequestException {

        Student student = studentService.findById(id);

        if (student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu id-ul=%s nu a fost gasit!", id));
        }

        Preference preference = preferenceService.findById(preferenceId);

        if (preference == null) {
            throw new ResourceNotFoundException(String.format("Preferinta cu id-ul=%s nu a fost gasita!", id));
        }

        boolean exists = student.getPreferences().stream().filter(p -> p != null).anyMatch(p -> p.getId().equals(preferenceId));

        if (!exists) {
            throw new BadRequestException(String.format("Preferinta cu id-ul=%s nu apartine studentului cu id-ul=%s", preferenceId, id));
        }

        student.removePreference(preference);

        // get answers of student if any
//        List<Answer> answers = student.getAnswers().stream().filter(a -> a.getQuestion().getProject().equals(preference.getProject())).collect(Collectors.toList());
//        answers.forEach(a -> student.removeAnswer(a));

        studentService.update(student);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @RequestMapping(value = "/{id}/preferences/reorder", method = RequestMethod.PUT)
    public ResponseEntity<?> reorderPreferencesListForStudent(@PathVariable Long id,
                                                              @RequestBody List<PreferenceDto> preferences) throws ResourceNotFoundException, BadRequestException {

        Student student = studentService.findById(id);

        if (student == null) {
            throw new ResourceNotFoundException(String.format("Studentul cu id-ul=%s nu a fost gasit!", id));
        }

        List<Preference> preferencesDb = student.getPreferences();


        // TODO: find a better way to solve this
        for(Preference p : preferencesDb) {
            if (p == null) {
                continue;
            }
            PreferenceDto preference = preferences.stream().filter(pref -> pref.getId().equals(p.getId())).findFirst().orElse(null);

            if (preference == null) {
                throw new BadRequestException(String.format("Preferinta cu id-ul=%s nu se afla in lista de preferinte a studentului!", preference.getId()));
            }

            p.setIndex(preferences.indexOf(preference));
        }

        studentService.update(student);
        return ResponseEntity.noContent().build();
    }


}
