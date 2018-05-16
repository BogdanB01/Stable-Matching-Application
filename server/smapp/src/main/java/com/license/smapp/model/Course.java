package com.license.smapp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.license.smapp.common.EntityIdResolver;

import javax.persistence.*;
import java.util.List;

/**
 * The Course JPA Entity
 */

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @SequenceGenerator(name = "course_seq", sequenceName = "course_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    private Long id;
    private String abbreviation;
    private String name;
    private int year;
    private int semester;
    private String code;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Grade> grades;

    public Course() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
