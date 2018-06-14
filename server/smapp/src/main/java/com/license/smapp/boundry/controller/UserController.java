package com.license.smapp.boundry.controller;

import com.license.smapp.boundry.dto.UserDto;
import com.license.smapp.boundry.exceptions.ResourceNotFoundException;
import com.license.smapp.entity.model.User;
import com.license.smapp.control.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * Get a list with all the User objects from the database
     * @return List with the User Objects and a message if the request was successfully
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> users = modelMapper.map(userService.findAll(), new TypeToken<List<UserDto>>() {}.getType());
        return ResponseEntity.ok(users);
    }

    /**
     * Get a paginated list of User object from the database
     * @return a List with User object
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    Page<UserDto> getUsersByPage(@RequestParam(value = "email", required = false, defaultValue = "") String email,
                                 Pageable pageable) {
        Page<User> page = userService.listAllByPage(email, pageable);
        Type targetListType = new TypeToken<List<UserDto>>() {}.getType();
        return new PageImpl<>(modelMapper.map(page.getContent(), targetListType), pageable, page.getTotalElements());
    }

    /**
     * Find an User by id
     *
     * @param id the Id to search by
     *
     * @return An User Object / message if the object was successfully found or not
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        User user = userService.findById(id);

        if (user == null) {
            throw new ResourceNotFoundException(String.format("Utilizatorul cu id-ul=%s nu a fost gasit!"));
        }

        return ResponseEntity.ok(user);
    }

    /**
     * Delete a particular User object from database
     *
     * @param id the id of the User to delete
     * @return message if the object was successfully deleted
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) throws ResourceNotFoundException {

        User user = userService.findById(id);

        if (user == null) {
            throw new ResourceNotFoundException(String.format("Utilizatorul cu id-ul=%s nu a fost gasit!", id));
        }
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
