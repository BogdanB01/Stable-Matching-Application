package com.license.smapp.entity.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "preferences")
public class Preference implements Comparable<Preference>, Cloneable{

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

    private Integer index;

    private Double avg;

    private Integer pos;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

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

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    @PrePersist
    public void prePersist() {
        this.avg = this.student.getAvg();
        this.pos = Integer.MAX_VALUE;
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

    @Override
    public int compareTo(Preference preference) {

        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this.pos < preference.getPos()) {
            return BEFORE;
        }

        if (this.pos > preference.getPos()) {
            return AFTER;
        }

        if (this.getAvg() > preference.getAvg()) {
            return BEFORE;
        } else if (this.getAvg() < preference.getAvg()) {
            return AFTER;
        } else {
            if (this.getSubmittedAt().before(preference.getSubmittedAt())) {
                return BEFORE;
            } else if (this.getSubmittedAt().after(preference.getSubmittedAt())) {
                return AFTER;
            } else {
                return EQUAL;
            }
        }
    }

    @Override
    public Preference clone() {
        Preference clone = null;
        try {
            clone = (Preference) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

}
