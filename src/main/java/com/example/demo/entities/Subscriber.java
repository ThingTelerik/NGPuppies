package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "subscribers")
@EntityListeners(AuditingEntityListener.class)
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String phoneNumber;

    //personal details
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String EGN;

    private String address;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    @JsonIgnore
    private Client bank;


    @ManyToMany
    @JoinTable(name = "services_subscribers",
            joinColumns = @JoinColumn(name = "subscriber_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    @JsonIgnore
    private Collection<Services> services;

    public Subscriber() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEGN() {
        return EGN;
    }

    public void setEGN(String EGN) {
        this.EGN = EGN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Client getBank() {
        return bank;
    }

    public void setBank(Client bank) {
        this.bank = bank;
    }

    public void addService(Services services) {
        this.services.add(services);
    }

    public void setServices(Collection<Services> services) {
        this.services = services;
    }

    public Collection<Services> getServices() {
        return services;
    }
}
