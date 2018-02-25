package com.license.StableMatchingApplication.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 *  The Student JPA entity
 */

@Entity
@Table(name="students")
public class Student extends User{

    @Column(name="registration_number")
    private String registrationNumber;

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;

    public Student(){

    }

    public List<Grade> getGrades(){
        return grades;
    }

    public String getRegistrationNumber(){
        return registrationNumber;
    }

    public void setGrades(List<Grade> grades){
        this.grades = grades;
    }

    public void setRegistrationNumber(String registrationNumber){
        this.registrationNumber = registrationNumber;
    }


}
