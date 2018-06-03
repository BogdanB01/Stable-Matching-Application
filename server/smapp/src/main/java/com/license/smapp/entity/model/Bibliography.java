package com.license.smapp.entity.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.license.smapp.common.EntityIdResolver;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bibliographies")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = EntityIdResolver.class,
        scope = Bibliography.class
)
public class Bibliography {

    @Id
    @SequenceGenerator(name = "bibliography_seq", sequenceName = "bibliography_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bibliography_seq")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Bibliography() {}

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bibliography that = (Bibliography) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
