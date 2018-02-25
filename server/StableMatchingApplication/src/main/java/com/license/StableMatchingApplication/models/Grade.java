package com.license.StableMatchingApplication.models;


import javax.persistence.*;

/**
 *  The Grade JPA entity
 */

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "stud_id")
    private Student student;

    private int value;

    public Grade() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse(){
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public int getValue() {
        return value;
    }
}
