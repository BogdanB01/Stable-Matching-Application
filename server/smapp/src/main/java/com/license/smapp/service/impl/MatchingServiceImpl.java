package com.license.smapp.service.impl;

import com.license.smapp.model.Project;
import com.license.smapp.model.Student;
import com.license.smapp.repository.LecturerRepository;
import com.license.smapp.repository.ProjectRepository;
import com.license.smapp.repository.StudentRepository;
import com.license.smapp.service.MatchingService;
import com.license.smapp.service.ProjectService;
import com.license.smapp.util.solver.StableMatchingSolver;
import com.license.smapp.util.solver.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MatchingServiceImpl implements MatchingService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Map<Student, Project> match() {
        Problem problem = new Problem(studentRepository, projectRepository);
        StableMatchingSolver solver = new StableMatchingSolver(problem);
        Map<Student, Project> matches = solver.solve();
        return matches;
    }
}
