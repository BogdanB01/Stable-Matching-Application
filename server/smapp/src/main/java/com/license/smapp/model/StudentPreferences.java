package com.license.smapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "students_preferences")
public class StudentPreferences {

    @Id
    @SequenceGenerator(name = "stud_pref_seq", sequenceName = "stud_pref_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stud_pref_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "submitted_at")
    @Temporal(TemporalType.DATE)
    private Date submittedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }
}
