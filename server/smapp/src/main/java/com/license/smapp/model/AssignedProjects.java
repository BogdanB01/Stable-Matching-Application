package com.license.smapp.model;

import javax.persistence.*;


@Entity
@Table(name = "assigned_projects")
public class AssignedProjects {

    @Id
    @SequenceGenerator(name = "assigned_stud_seq", sequenceName = "assigned_stud_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assigned_stud_seq")
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "project_id")
    private Long projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
