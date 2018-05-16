package com.license.smapp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.license.smapp.common.EntityIdResolver;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class File {

    @Id
    @SequenceGenerator(name = "file_seq", sequenceName = "file_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    private Long id;

    private String path;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
