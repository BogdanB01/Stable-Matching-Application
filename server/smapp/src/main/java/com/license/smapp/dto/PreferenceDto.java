package com.license.smapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.license.smapp.model.Answer;

import java.util.List;

public class PreferenceDto {
    private Long studentId;
    private Long projectId;

  //  @JsonProperty("answers")
    //private List<AnswerDto> answers;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

//    public List<AnswerDto> getAnswers() {
//        return answers;
//    }
//
//    public void setAnswers(List<AnswerDto> answers) {
//        this.answers = answers;
//    }
}
