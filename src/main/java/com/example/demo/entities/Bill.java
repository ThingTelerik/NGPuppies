package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Services service;

    @NotNull
    @Column(name = "startDate")
    private LocalDate startDate;

    @NotNull
    @Column(name = "endDate")
    private LocalDate endDate;

    private LocalDate paymentDate;

    @NotNull
    @Column(name = "amount")
    private double amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "subscriber_id", referencedColumnName = "id")
    private Subscriber subscriber;


    public Bill() {
    }


    public long getId() {
        return id;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }



    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {

        this.paymentDate = paymentDate;
    }


}
