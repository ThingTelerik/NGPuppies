package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {

    @Column(name = "Eik", unique = true)
    private String EIK;


    public Client() {
    }

    public Client(String name, String password, String EIK) {
        super(name, password,RoleType.ROLE_CLIENT);
        setEIK(EIK);
    }

    public String getEIK() {
        return EIK;
    }

    public void setEIK(String EIK) {
        this.EIK = EIK;
    }
}
