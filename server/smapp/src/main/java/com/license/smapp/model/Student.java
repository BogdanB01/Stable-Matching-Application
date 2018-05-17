package com.license.smapp.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.license.smapp.common.EntityIdResolver;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The Student JPA entity
 */

@Entity
@Table(name="STUDENTS")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = EntityIdResolver.class,
        scope = Student.class
)
public class Student extends User{

    @Column(name="registration_number")
    private String registrationNumber;

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name ="index")
    private List<Preference> preferences;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @OneToOne(mappedBy = "student")
    private AssignedProjects assignedProject;

    public Student() {

    }

    public AssignedProjects getAssignedProject() {
        return assignedProject;
    }

    public void setAssignedProject(AssignedProjects assignedProject) {
        this.assignedProject = assignedProject;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Preference> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Preference> preferences) {
        this.preferences = preferences;
    }

    public void addPreference(Preference preference) {
        preference.setSubmittedAt(new Date());
        preference.setStudent(this);
        this.preferences.add(preference);
    }

    public void removePreference(Preference preference) {
        this.preferences.remove(preference);
        if (preference != null) {
            preference.setStudent(null);
        }
    }

    public void addGrade(Grade grade) {
        grade.setStudent(this);
        grades.add(grade);
    }

    public void removeGrade(Grade grade) {
        this.grades.remove(grade);
        if (grade != null) {
            grade.setStudent(null);
        }
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer) {
        answer.setStudent(this);
        answers.add(answer);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        if (answer != null) {
            answer.setStudent(null);
        }
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Transient
    public Double getAvg() {
        return this.grades.stream().mapToInt(g -> g.getValue()).average().getAsDouble();
    }
}
