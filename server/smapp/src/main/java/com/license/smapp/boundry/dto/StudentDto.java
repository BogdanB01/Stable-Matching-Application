package com.license.smapp.boundry.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;


public class StudentDto extends UserDto {
    private String registrationNumber;

    @JsonBackReference
    private ProjectDto project;

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
