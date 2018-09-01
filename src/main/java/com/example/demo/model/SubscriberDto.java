package com.example.demo.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class SubscriberDto {

    @Length(min = 10)
    private String phoneNumber;

    @NotNull
    @Length(max =30)
    private String firstName;

    @NotNull
    @Length(max =30)
    private String lastName;

    @NotNull
    @Length(min = 10, max = 10)
    private String EGN;

    private String address;

    public SubscriberDto() {
    }

    public SubscriberDto(@Length(min = 10) String phoneNumber, @NotNull @Length(max = 30) String firstName, @NotNull @Length(max = 30) String lastName, @NotNull @Length(min = 10, max = 10) String EGN, String address) {
        setPhoneNumber(phoneNumber);
        setFirstName(firstName);
        setLastName(lastName);
        setEGN(EGN);
        setAddress(address);
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
}
