package com.license.smapp.control.service.impl;

import com.license.smapp.entity.model.Project;
import com.license.smapp.entity.model.Student;
import com.license.smapp.entity.repository.ProjectRepository;
import com.license.smapp.entity.repository.StudentRepository;
import com.license.smapp.control.service.MatchingService;
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
