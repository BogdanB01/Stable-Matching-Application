package com.license.smapp.util.solver;

import com.license.smapp.entity.model.Project;
import com.license.smapp.entity.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class StableMatchingSolver {
    private Problem problem;

    Logger logger = LoggerFactory.getLogger(StableMatchingSolver.class);

    public StableMatchingSolver(Problem problem) {
        this.problem = problem;
    }

    public Map<Student, Project> solve() {
        while(problem.thereIsAFreeStudentWithNoEmptyList()) {
            Student si = problem.getFreeStudentWithNoEmptyList();
            Project pj = si.getFirstProject();
            // provisionally assign si to pj

            si.setProject(pj);
            pj.addStudent(si);

            if (pj.isOverSubscribed()) {
                Student sr = pj.getWorstAssignedStudent();

                pj.removeStudent(sr);
                sr.setProject(null);
            }
            if (pj.isFull()) {
                Student sr = pj.getWorstAssignedStudent();
                pj.deleteSuccessors(sr);
            }
        }

        problem.printSolution();
        problem.saveSolution();
        return problem.getSolution();
    }
}
