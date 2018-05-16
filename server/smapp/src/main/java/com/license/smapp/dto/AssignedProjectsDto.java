package com.license.smapp.dto;

public class AssignedProjectsDto {

    private Long id;
    private StudentDto student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
    }
}
