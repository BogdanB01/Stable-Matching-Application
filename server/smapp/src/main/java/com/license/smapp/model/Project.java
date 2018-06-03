package com.license.smapp.model;

import com.fasterxml.jackson.annotation.*;
import com.license.smapp.common.EntityIdResolver;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.SortComparator;
import org.hibernate.annotations.SortNatural;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

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

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Bibliography> bibliographies;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
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

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @SortNatural
    private SortedSet<Preference> preferences;

    private boolean active;

    @PrePersist
    private void prePersist() {
        this.active = true;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
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

    public void addStudent(Student student) {
        student.setProject(this);
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        if (student != null) {
            student.setProject(null);
        }
    }

    public void addTag(Tag newTag) {
        tags.add(newTag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }


    public SortedSet<Preference> getPreferences() {
        return preferences;
    }

    public void setPreferences(SortedSet<Preference> preferences) {
        this.preferences = preferences;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    /*
     * SPA helper methods
     */
    public boolean isOverSubscribed() {
        return (capacity - students.size()) < 0;
    }

    public boolean isFull() {
        return (capacity - students.size()) == 0;
    }

    public Student getWorstAssignedStudent() {
        Student worstStudent = null;

        for (Preference preference : preferences){
            if (preference.getStudent().getProject() == null) {
                continue;
            }
            if (preference.getStudent().getProject().getId().equals(this.getId())) {
                worstStudent = preference.getStudent();
            }
        }

        return worstStudent;
    }

    public void deleteSuccessors(Student student) {
        boolean canDelete = false;
        for (Preference preference : preferences) {
            if (canDelete) {
                preference.getStudent().removeProject(this);
            }
            if (preference.getStudent().equals(student)) {
                canDelete = true;
            }
        }
    }


}
