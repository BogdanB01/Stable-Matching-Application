package com.license.smapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.license.smapp.model.Answer;

import java.util.List;

public class CreatePreferenceDTO {

    private Long projectId;

    private List<AnswerDto> answers;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }
}
