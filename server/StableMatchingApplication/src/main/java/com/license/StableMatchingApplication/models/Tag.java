package com.license.StableMatchingApplication.models;

import javax.persistence.*;
import java.util.List;

/**
 * The Tag JPA entity
 */

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Project> projects;

    public Tag(){

    }

    public List<Project> getProjects() { return projects; }

    public Long getId() {
        return  id;
    }

    public String getName() {
        return name;
    }

    public void setProjects(List<Project> projects) { this.projects = projects; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
