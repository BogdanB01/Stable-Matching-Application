package com.license.smapp.dto;

import com.license.smapp.model.Answer;
import com.license.smapp.model.Grade;

import java.util.List;

public class StudentDetailsDto {

    List<Answer> answers;
    List<Grade> grades;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
