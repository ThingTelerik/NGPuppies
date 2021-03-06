package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {

    @Column(name = "Eik", unique = true)
    private String EIK;


    @OneToMany(mappedBy = "bank", orphanRemoval = true)
    @Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.DELETE })
    @JsonIgnore
    List<Subscriber> subscribers;

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


    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }
}
