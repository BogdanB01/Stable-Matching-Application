package com.license.smapp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.license.smapp.common.EntityIdResolver;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "preferences")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = EntityIdResolver.class,
        scope = Preference.class
)
public class Preference {

    @Id
    @SequenceGenerator(name = "stud_pref_seq", sequenceName = "stud_pref_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stud_pref_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "submitted_at")
    @Temporal(TemporalType.TIMESTAMP)
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


    @Override
    public String toString() {
        return "Preference{" +
                "id=" + id +
                ", student=" + student +
                ", project=" + project +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
