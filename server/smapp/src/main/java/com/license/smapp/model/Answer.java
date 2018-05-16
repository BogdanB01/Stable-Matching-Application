package com.license.smapp.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.license.smapp.common.EntityIdResolver;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @SequenceGenerator(name = "answer_seq", sequenceName = "answer_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question=" + question +
                ", answer='" + answer + '\'' +
                '}';
    }
}
