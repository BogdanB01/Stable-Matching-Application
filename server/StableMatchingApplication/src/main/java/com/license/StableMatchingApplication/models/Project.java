package com.license.StableMatchingApplication.models;


import javax.persistence.*;
import java.util.List;

/**
 *  The Project JPA entity
 */

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private String requirements;

    @ManyToOne
    @JoinColumn(name="prof_id")
    private Professor professor;

    @ManyToMany
    @JoinTable(name="project_tags", joinColumns = @JoinColumn(name="project_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id")
    )
    private List<Tag> tags;

    public Project(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getRequirements(){
        return requirements;
    }

    public List<Tag> getTags(){
        return tags;
    }
}
