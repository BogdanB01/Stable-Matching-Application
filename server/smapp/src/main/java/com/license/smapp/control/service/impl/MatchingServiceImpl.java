package com.license.smapp.control.service.impl;

import com.license.smapp.common.TimeProvider;
import com.license.smapp.entity.model.History;
import com.license.smapp.entity.model.Project;
import com.license.smapp.entity.model.Student;
import com.license.smapp.entity.repository.HistoryRepository;
import com.license.smapp.entity.repository.ProjectRepository;
import com.license.smapp.entity.repository.StudentRepository;
import com.license.smapp.control.service.MatchingService;
import com.license.smapp.util.solver.StableMatchingSolver;
import com.license.smapp.util.solver.Problem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MatchingServiceImpl implements MatchingService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    TimeProvider timeProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchingService.class);

    @Override
    public Map<Student, Project> match() {
        Problem problem = new Problem(studentRepository, projectRepository);
        StableMatchingSolver solver = new StableMatchingSolver(problem);
        Map<Student, Project> matches = solver.solve();
        return matches;
    }

    @Override
    public void saveSolution(Map<Student, Project> matches) {
        for (Map.Entry<Student, Project> entry : matches.entrySet()) {

            History history = new History();
            if (entry.getValue() != null) {
               // LOGGER.error(entry.getKey().getName() +  " + " + entry.getValue().getTitle());
                Student student = studentRepository.findOne(entry.getKey().getId());
                Project project = projectRepository.findOne(entry.getValue().getId());

                student.setProject(project);
                studentRepository.save(student);

                history.setStudentName(student.getName());
                history.setProjectTitle(project.getTitle());
            } else {
                history.setStudentName(entry.getKey().getName());
                history.setProjectTitle("none");
            }
            history.setYear(timeProvider.now().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear());
          //  records.add(history);
            historyRepository.save(history);
        }

    }
}
