package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin  extends User{

    @Column(name = "email", unique = true)
    private String email;

    public Admin() {
    }

    public Admin(String name, String password, String email) {
        super(name, password, RoleType.ROLE_ADMIN);
        setEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
