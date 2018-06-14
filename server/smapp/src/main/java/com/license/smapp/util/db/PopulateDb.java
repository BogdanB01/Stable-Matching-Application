//package com.license.smapp.util.db;
//
//import com.license.smapp.entity.model.Lecturer;
//import com.license.smapp.entity.model.Role;
//import com.license.smapp.entity.model.Student;
//import com.license.smapp.entity.model.User;
//import com.license.smapp.entity.repository.LecturerRepository;
//import com.license.smapp.entity.repository.RoleRepository;
//import com.license.smapp.entity.repository.StudentRepository;
//import com.license.smapp.entity.repository.UserRepository;
//import io.swagger.annotations.Authorization;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.stream.Collectors;
//
///**
// * This class will run every time the application is started
// */
//
//@Component
//public class PopulateDb implements ApplicationRunner{
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private LecturerRepository lecturerRepository;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    public void run(ApplicationArguments args) {
//        Role studentRole = null;
//        Role lecturerRole = null;
//        Role adminRole = null;
//
//        if (roleRepository.count() == 0) {
//            // create three category of roles
//            studentRole = this.createRole("ROLE_STUDENT");
//            lecturerRole = this.createRole("ROLE_LECTURER");
//            adminRole = this.createRole("ROLE_ADMIN");
//        }
//
//        if (userRepository.count() == 0) {
//            Lecturer firstLecturer = this.createLecturer("Profesorul X", "profesor@email.com", "asddsa", lecturerRole);
//            Lecturer secondLecturer = this.createLecturer("Profesorul Y", "profesor@email.com", "asddsa", lecturerRole);
//
//            User user = this.createAdmin("Administator", "admin@email.com", "asddsa", adminRole);
//
//
//        }
//    }
//
//
//    private Role createRole(String roleName) {
//        Role role = new Role();
//        role.setName(roleName);
//        return roleRepository.save(role);
//    }
//
//    private Lecturer createLecturer(String name, String email, String password, Role role) {
//        Lecturer lecturer = new Lecturer();
//        lecturer.setName(name);
//        lecturer.setEmail(email);
//        lecturer.setPassword(password);
//        lecturer.setRoles(Collections.singletonList(role));
//
//        return lecturerRepository.save(lecturer);
//    }
//
//    public User createAdmin(String name, String email, String password, Role role) {
//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//        user.setPassword(password);
//        user.setRoles(Collections.singletonList(role));
//
//        return userRepository.save(user);
//    }
//
//    public Student createStudent(String name, String email, String password, Role role) {
//        Student student = new Student();
//        student.setName(name);
//        student.setEmail(email);
//        student.setPassword(password);
//        student.setRoles(Collections.singletonList(role));
//
//        return student;
//    }
//}
