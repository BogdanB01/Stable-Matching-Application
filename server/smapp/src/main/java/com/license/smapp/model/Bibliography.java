package com.license.smapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "bibliographies")
public class Bibliography {

    @Id
    @SequenceGenerator(name = "bibliography_seq", sequenceName = "bibliography_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bibliography_seq")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    public Bibliography() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
