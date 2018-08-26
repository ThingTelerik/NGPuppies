package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "subscribers")
@EntityListeners(AuditingEntityListener.class)
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String phoneNumber;

    //personal details
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String EGN;

    private String address;
    @ManyToOne
    @JoinColumn (name = "bank_id", referencedColumnName = "id")
    private User subscribersBank;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "services_subscribers",
            joinColumns = @JoinColumn(name = "subscriber_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    private Set<Services> services;

    public Subscriber(){

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

    public User getSubscribersBank() {
        return subscribersBank;
    }

    public void setSubscribersBank(User subscribersBank) {
        this.subscribersBank = subscribersBank;
    }

    public Set<Services> getServices() {
        return services;
    }

    public void setServices(Set<Services> services) {
        this.services = services;
    }
}
