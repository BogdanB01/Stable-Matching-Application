package com.license.smapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.license.smapp.model.Tag;

import java.util.List;

public class ProjectDto {

    private Long id;
    private String title;
    private String description;
    private Integer capacity;
    @JsonProperty("lecturer")
    private LecturerDto lecturer;
    private List<QuestionDto> questions;
    private List<BibliographyDTO> bibliographies;
    private List<TagDto> tags;
    private List<AssignedProjectsDto> assignedProjects;
    private FileDto file;
    private boolean active;

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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LecturerDto getLecturer() {
        return lecturer;
    }

    public void setLecturer(LecturerDto lecturer) {
        this.lecturer = lecturer;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public List<BibliographyDTO> getBibliographies() {
        return bibliographies;
    }

    public void setBibliographies(List<BibliographyDTO> bibliographies) {
        this.bibliographies = bibliographies;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    public List<AssignedProjectsDto> getAssignedProjects() {
        return assignedProjects;
    }

    public void setAssignedProjects(List<AssignedProjectsDto> assignedProjects) {
        this.assignedProjects = assignedProjects;
    }

    public FileDto getFile() {
        return file;
    }

    public void setFile(FileDto file) {
        this.file = file;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
