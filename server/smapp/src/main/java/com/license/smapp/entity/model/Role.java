package com.license.smapp.entity.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * The Role JPA entity
 */

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    List<User> users;

    public Role() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
