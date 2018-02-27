package com.license.smapp.model;


import javax.persistence.*;
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

    @OneToMany(mappedBy = "student")
    private List<StudentPreferences> preferences;

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

    public List<StudentPreferences> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<StudentPreferences> preferences) {
        this.preferences = preferences;
    }

    public void addPreference(StudentPreferences preference) {
        this.preferences.add(preference);
    }
}
