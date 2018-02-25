package com.license.smapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.PropertyKey;
import org.hibernate.annotations.Cascade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 *  The Lecturer JPA Entity
 */

@Entity
@Table(name = "LECTURERS")
public class Lecturer extends User{

    @Column(name = "lecturer_id")
    private String lecturerId;

    @Column(name = "cabinet_number")
    private String cabinetNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "lecturer")
    private List<Project> projects;

    public Lecturer() {}

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getCabinetNumber() {
        return cabinetNumber;
    }

    public void setCabinetNumber(String cabinetNumber) {
        this.cabinetNumber = cabinetNumber;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
