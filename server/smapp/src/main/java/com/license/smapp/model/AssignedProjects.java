package com.license.smapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.license.smapp.common.EntityIdResolver;

import javax.persistence.*;


@Entity
@Table(name = "assigned_projects")
public class AssignedProjects {

    @Id
    @SequenceGenerator(name = "assigned_stud_seq", sequenceName = "assigned_stud_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assigned_stud_seq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
