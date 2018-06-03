package com.license.smapp.boundry.dto;

public class FullAnswerDto {
    private Long id;
    private QuestionDto question;
    private StudentDto student;
    private String answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDto question) {
        this.question = question;
    }

    public StudentDto getStudent() {
        return student;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
