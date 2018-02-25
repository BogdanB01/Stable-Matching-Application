package com.license.smapp.model;

import javax.persistence.*;

/**
 * The Grade JPA Entity
 */

@Entity
@Table(name = "GRADES")
public class Grade {

    @Id
    @SequenceGenerator(name = "grade_seq", sequenceName = "grade_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name="stud_id")
    private Student student;

    private int value;

    public Grade() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

