package com.license.smapp.entity.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "app_state")
public class ApplicationState {

    public ApplicationState() {}

    public ApplicationState(Long id, State state) {
        this.id = id;
        this.state = state;
    }

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private State state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
