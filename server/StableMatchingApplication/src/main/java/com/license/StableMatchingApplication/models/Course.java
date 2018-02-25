package com.license.StableMatchingApplication.models;

import javax.persistence.*;
import java.util.List;


/**
 *  The Course JPA entity
 */

@Entity
@Table(name="courses")
public class Course {

    @Id
    @GeneratedValue
    private Long id;
    private String abbreviation;
    private String name;
    private int year;
    private int semester;

    @OneToMany(mappedBy = "course")
    private List<Grade> grades;

    public Course() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public String getAbbreviation(){
        return abbreviation;
    }

}
