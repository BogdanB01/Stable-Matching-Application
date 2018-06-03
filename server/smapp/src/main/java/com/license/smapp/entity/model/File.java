package com.license.smapp.entity.model;


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
