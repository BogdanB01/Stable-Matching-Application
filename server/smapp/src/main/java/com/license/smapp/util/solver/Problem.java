package com.license.smapp.util.solver;

import com.license.smapp.model.*;
import com.license.smapp.repository.ProjectRepository;
import com.license.smapp.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Component
public class Problem {

    private StudentRepository studentRepository;
    private ProjectRepository projectRepository;

    private List<Student> students;
    private List<Project> projects;

    private Logger LOGGER = LoggerFactory.getLogger(Problem.class);

    public Problem(StudentRepository studentRepository,ProjectRepository projectRepository) {
        this.studentRepository = studentRepository;
        this.projectRepository = projectRepository;

        initialize();
    }

    private void initialize() {
        students = studentRepository.findAll();
        studentRepository.detach(students);

        projects = projectRepository.findAll();
        projectRepository.detach(projects);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Project> getProjects() { return projects; }

    public boolean thereIsAFreeStudentWithNoEmptyList() {
        for (Student student : students) {
            if (student.isFree() && student.getPreferences().size() > 0) {
                return true;
            }
        }
        return false;
    }

    public Student getFreeStudentWithNoEmptyList() {
        for (Student student : students) {
            if (student.isFree() && student.getPreferences().size() > 0) {
                return student;
            }
        }
        return null;
    }


    public void printSolution() {

        for (Student student : students) {
            if (student.getProject() == null) {
                LOGGER.error("Studentul cu numele " + student.getName() + " nu are proiect asignat");
            } else {
                LOGGER.error(student.getName() + "  " + student.getProject().getTitle());
            }
        }

    }

    public HashMap<Student, Project> getSolution() {

        HashMap<Student, Project> result = new HashMap<>();

        for (Student student : students) {
            if (student.getProject() != null) {
                result.put(student, student.getProject());
            } else {
                result.put(student, null);
            }
        }
        return result;

    }

    public void saveSolution() {
        for (Student s: students) {
            if (s.getProject() != null) {
                Student student = studentRepository.findOne(s.getId());
                Project project = projectRepository.findOne(s.getProject().getId());

                project.addStudent(student);
                projectRepository.save(project);
            }
        }
    }


}
