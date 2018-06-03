package com.license.smapp.control.service.impl;

import com.license.smapp.boundry.exceptions.ResourceNotFoundException;
import com.license.smapp.entity.model.Course;
import com.license.smapp.entity.model.Grade;
import com.license.smapp.entity.model.Role;
import com.license.smapp.entity.model.Student;
import com.license.smapp.entity.repository.CourseRepository;
import com.license.smapp.entity.repository.RoleRepository;
import com.license.smapp.entity.repository.StudentRepository;
import com.license.smapp.control.service.CsvService;
import com.license.smapp.control.service.EmailService;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class CsvServiceImpl implements CsvService {

    private Logger LOGGER = LoggerFactory.getLogger(CsvService.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void parseStudentsFile(MultipartFile multipartFile) {
        File file = convertMultipartFileToFile(multipartFile);
        Role role = roleRepository.findByName("ROLE_STUDENT");
        try (Reader reader = new FileReader(file)) {
            ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            strategy.setType(Student.class);
            String[] memberFieldsToBindTo = {"ignore", "name", "registrationNumber", "email"};
            strategy.setColumnMapping(memberFieldsToBindTo);

            CsvToBean<Student> csvToBean = new CsvToBeanBuilder(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<Student> studentIterator = csvToBean.iterator();

            List<Student> students = new ArrayList<>();
            while (studentIterator.hasNext()) {
                Student student = studentIterator.next();
                student.setPassword(UUID.randomUUID().toString());
                student.addRole(role);

                students.add(student);

                // Disabled during development
//                Email email = emailService.getEmail(student, "Cont creat");
//                emailService.sendEmail(email);
            }
            studentRepository.save(students);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseLecturersFile(MultipartFile file) {

    }

    @Override
    public void parseGradesFile(MultipartFile multipartFile) throws ResourceNotFoundException {
        File file = convertMultipartFileToFile(multipartFile);
        try (
                Reader reader = new FileReader(file);
                CSVReader csvReader = new CSVReader(reader)
        ) {
            String[] nextRecord;

            while((nextRecord = csvReader.readNext()) != null) {
                LOGGER.error(nextRecord[0] + " " + nextRecord[1] + " " + nextRecord[2]);
                Student student = studentRepository.findByRegistrationNumber(nextRecord[0]);

                if (student == null) {
                    throw new ResourceNotFoundException(String.format("Studentul cu numarul matricol=%s nu a fost gasit!", nextRecord[0]));
                }

                Course course = courseRepository.findByAbbreviation(nextRecord[1]);

                if (course == null) {
                    throw new ResourceNotFoundException(String.format("Cursul %s nu a fost gasit!", nextRecord[1]));
                }

                Grade grade = new Grade();
                grade.setCourse(course);
                grade.setValue(Integer.parseInt(nextRecord[2]));
                student.addGrade(grade);
                studentRepository.save(student);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseCoursesFile(MultipartFile multipartFile) {
        File file = convertMultipartFileToFile(multipartFile);
        try (Reader reader = new FileReader(file);) {

            ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            strategy.setType(Course.class);
            String[] memberFieldsToBindTo = {"code", "abbreviation", "name", "year", "semester"};
            strategy.setColumnMapping(memberFieldsToBindTo);

            CsvToBean<Course> csvToBean = new CsvToBeanBuilder(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<Course> courseIterator = csvToBean.iterator();

            List<Course> courses = new ArrayList<>();
            while (courseIterator.hasNext()) {
                Course course = courseIterator.next();
                courses.add(course);
            }

            courseRepository.save(courses);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return convFile;
    }
}
