package com.license.smapp.controller;

import com.license.smapp.dto.CreateLecturerDTO;
import com.license.smapp.model.Lecturer;
import com.license.smapp.service.LecturerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
