package com.license.smapp.model;

import com.fasterxml.jackson.annotation.*;
import com.license.smapp.common.EntityIdResolver;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 *  The Tag JPA Entity
 */

@Entity
@Table(name = "tags")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = EntityIdResolver.class,
        scope = Tag.class
)
@JsonIgnoreProperties({"projects"})
public class Tag {

    @Id
    @SequenceGenerator(name = "tag_seq", sequenceName = "tag_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_seq")
    private Long id;
    @JsonUnwrapped
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Project> projects;

    public Tag() {}

    public Long getId() {
        return id;
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

    public void removeProject(Project project) {
        projects.remove(project);
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
