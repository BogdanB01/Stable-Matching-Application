package com.license.StableMatchingApplication.models;

import javafx.print.PageOrientation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 *  The professor JPA entity
 */

@Entity
@Table(name="professors")
public class Professor extends User{

    @Column(name="prof_id")
    private String profId;

    @Column(name="cabinet_number")
    private String cabinetNumber;

    @OneToMany(mappedBy = "professor")
    private List<Project> projects;

    public Professor(){

    }

    public void setProfId(String profId){
        this.profId = profId;
    }

    public void setProjects(List<Project> projects){
        this.projects = projects;
    }

    public void setCabinetNumber(String cabinetNumber){
        this.cabinetNumber = cabinetNumber;
    }

    public String getProfId(){
        return profId;
    }

    public String getCabinetNumber() {
        return cabinetNumber;
    }

    public List<Project> getProjects() {
        return projects;
    }
}
