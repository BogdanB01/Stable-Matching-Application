package com.license.smapp.model;

import javax.persistence.*;
import java.util.List;

/**
 *  The Tag JPA Entity
 */

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @SequenceGenerator(name = "tag_seq", sequenceName = "tag_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Project> projects;

    public Tag() {}

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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
