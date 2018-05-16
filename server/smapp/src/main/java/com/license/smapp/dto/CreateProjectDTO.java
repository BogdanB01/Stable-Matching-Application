package com.license.smapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.license.smapp.model.Bibliography;
import com.license.smapp.model.File;
import com.license.smapp.model.Tag;

import java.util.List;


public class CreateProjectDTO {

    private String title;
    private int capacity;
    private String description;
    private List<Tag> tags;
    private List<Bibliography> bibliographies;
    private List<QuestionDto> questions;
    @JsonProperty("file")
    private File file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
