package com.license.smapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * The Project JPA Entity
 */

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    private Long id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "project")
    @JsonBackReference
    private List<StudentPreferences> studentPreferences;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @ManyToMany
    @JoinTable(
            name="project_tags",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public Project() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<StudentPreferences> getStudentPreferences() {
        return studentPreferences;
    }

    public void setStudentPreferences(List<StudentPreferences> studentPreferences) {
        this.studentPreferences = studentPreferences;
    }
}
