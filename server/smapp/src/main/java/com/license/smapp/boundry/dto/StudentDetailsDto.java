package com.license.smapp.boundry.dto;

import java.util.List;

public class StudentDetailsDto {

    private List<FullAnswerDto> answers;
    private List<GradeDto> grades;
    private String personalNote;

    public String getPersonalNote() {
        return personalNote;
    }

    public void setPersonalNote(String personalNote) {
        this.personalNote = personalNote;
    }

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
