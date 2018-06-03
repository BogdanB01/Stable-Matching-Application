package com.license.smapp.dto;

import com.license.smapp.model.Answer;
import com.license.smapp.model.Grade;

import java.util.List;

public class StudentDetailsDto {

    private List<FullAnswerDto> answers;
    private List<GradeDto> grades;

    public List<FullAnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<FullAnswerDto> answers) {
        this.answers = answers;
    }

    public List<GradeDto> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeDto> grades) {
        this.grades = grades;
    }
}
