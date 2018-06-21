package com.license.smapp;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.license.smapp.control.service.MatchingService;
import com.license.smapp.control.service.impl.MatchingServiceImpl;
import com.license.smapp.entity.model.Student;
import com.license.smapp.entity.repository.ProjectRepository;
import com.license.smapp.entity.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class MatchingServiceImplTest {

    private Logger LOGGER = LoggerFactory.getLogger(MatchingServiceImplTest.class);
    @TestConfiguration
    static class MatchingServiceImplTestContextConfiguration {
        @Bean
        public MatchingService matchingService() {
            return new MatchingServiceImpl();
        }
    }

    @Autowired
    private MatchingService matchingService;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @Before
    public void setUp() {
        Student student = new Student();
        student.setName("Bogdan");

        Mockito.when(studentRepository.findByName(student.getName()))
                .thenReturn(student);
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "Bogdan";
        Student found = studentRepository.findByName(name);
        LOGGER.error("SALLLL");
        assert(found.getName()).equals(name);
    }
}
