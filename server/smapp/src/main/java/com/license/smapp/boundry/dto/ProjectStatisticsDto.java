package com.license.smapp.boundry.dto;

public class ProjectStatisticsDto {

    private String title;
    private Double averageStudentMean;
    private Integer numberOfStudentsThatAppliedForProject;
    private Double averagePositionInStudentsPreferences;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAverageStudentMean() {
        return averageStudentMean;
    }

    public void setAverageStudentMean(Double averageStudentMean) {
        this.averageStudentMean = averageStudentMean;
    }

    public Integer getNumberOfStudentsThatAppliedForProject() {
        return numberOfStudentsThatAppliedForProject;
    }

    public void setNumberOfStudentsThatAppliedForProject(Integer numberOfStudentsThatAppliedForProject) {
        this.numberOfStudentsThatAppliedForProject = numberOfStudentsThatAppliedForProject;
    }

    public Double getAveragePositionInStudentsPreferences() {
        return averagePositionInStudentsPreferences;
    }

    public void setAveragePositionInStudentsPreferences(Double averagePositionInStudentsPreferences) {
        this.averagePositionInStudentsPreferences = averagePositionInStudentsPreferences;
    }
}
