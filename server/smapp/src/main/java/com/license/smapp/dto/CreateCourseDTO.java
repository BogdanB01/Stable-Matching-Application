package com.license.smapp.dto;

import javax.validation.constraints.NotNull;

public class CreateCourseDTO {

    @NotNull
    private String abbreviation;
    @NotNull
    private String name;
    @NotNull
    private int year;
    @NotNull
    private int semester;
    @NotNull
    private String code;

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
