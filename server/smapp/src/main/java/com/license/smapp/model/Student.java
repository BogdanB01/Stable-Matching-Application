package com.license.smapp.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * The Student JPA entity
 */

@Entity
@Table(name="STUDENTS")
public class Student extends User{

    @Column(name="registration_number")
    private String registrationNumber;

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;

    public Student() {

    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
