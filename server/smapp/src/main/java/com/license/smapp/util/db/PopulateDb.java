package com.license.smapp.util.db;

import com.license.smapp.entity.model.Lecturer;
import com.license.smapp.entity.model.Role;
import com.license.smapp.entity.model.Student;
import com.license.smapp.entity.model.User;
import com.license.smapp.entity.repository.LecturerRepository;
import com.license.smapp.entity.repository.RoleRepository;
import com.license.smapp.entity.repository.StudentRepository;
import com.license.smapp.entity.repository.UserRepository;
import com.license.smapp.util.Constants;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * This class will run every time the application is started
 */

@Component
public class PopulateDb implements ApplicationRunner{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void run(ApplicationArguments args) {

        Role adminRole = roleRepository.findByName(Constants.ADMIN_USER);

        if (userRepository.count() == 0) {
            // create admin account
            User user = this.createAdmin("Administator", "admin@email.com", "admin", adminRole);
        }
    }

    public User createAdmin(String name, String email, String password, Role role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singletonList(role));

        return userRepository.save(user);
    }

}
