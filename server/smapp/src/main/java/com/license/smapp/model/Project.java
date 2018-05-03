package com.license.smapp.model;

import com.fasterxml.jackson.annotation.*;
import com.license.smapp.common.EntityIdResolver;
import org.hibernate.annotations.Cascade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

/**
 * The Project JPA Entity
 */

@Entity
@Table(name = "projects")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = EntityIdResolver.class,
        scope = Project.class
)
public class Project {

    @Id
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    private Long id;
    private String title;
    private String description;
    private Integer capacity;

//    @OneToMany(mappedBy = "project")
//    private List<StudentPreferences> studentPreferences;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Bibliography> bibliographies;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name="project_tags",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private File file;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignedProjects> assignedProjects;

    @PrePersist
    private void prePersist() {
        if (bibliographies != null) {
            bibliographies.forEach(c -> c.setProject(this));
        }
        if (questions != null) {
            questions.forEach(c -> c.setProject(this));
        }
        if (file != null) {
            file.setProject(this);
        }
    }

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

//    public List<StudentPreferences> getStudentPreferences() {
//        return studentPreferences;
//    }
//
//    public void setStudentPreferences(List<StudentPreferences> studentPreferences) {
//        this.studentPreferences = studentPreferences;
//    }

    public List<Bibliography> getBibliographies() {
        return bibliographies;
    }

    public void setBibliographies(List<Bibliography> bibliographies) {
        this.bibliographies = bibliographies;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<AssignedProjects> getAssignedProjects() {
        return assignedProjects;
    }

    public void setAssignedProjects(List<AssignedProjects> assignedProjects) {
        this.assignedProjects = assignedProjects;
    }

    public void addBibliography(Bibliography newBibliography) {
        newBibliography.setProject(this);
        bibliographies.add(newBibliography);
    }

    public void removeBibliography(Bibliography bibliography) {
        bibliographies.remove(bibliography);
        if(bibliography != null) {
            bibliography.setProject(null);
        }
    }

    public void addQuestion(Question newQuestion) {
        newQuestion.setProject(this);
        questions.add(newQuestion);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        if(question != null) {
            question.setProject(null);
        }
    }

    public void addTag(Tag newTag) {
        tags.add(newTag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public void assignProject(AssignedProjects assignedProjects) {
        assignedProjects.setProject(this);
        this.assignedProjects.add(assignedProjects);
    }

    public void unnasignStudent(AssignedProjects assigned) {
        assignedProjects.remove(assigned);
        if(assigned != null) {
            assigned.setProject(null);
        }
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", lecturer=" + lecturer +
                ", bibliographies=" + bibliographies +
                ", tags=" + tags +
                ", questions=" + questions +
                ", file=" + file +
                ", assignedProjects=" + assignedProjects +
                '}';
    }
}
