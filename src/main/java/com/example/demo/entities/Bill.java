package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

   @Enumerated
   @NotNull
    private Services service;

   @NotNull
   @Column(name = "startDate")
    private Instant startDate;

   @NotNull
   @Column(name = "endDate")
    private Instant endDate;

   @NotNull
   @Column(name = "amount")
    private double amount;

   @Enumerated
   @NotNull
   @Column(name = "currency")
    private  Currency currency;

   @NotNull
   @ManyToOne
   @JoinColumn(name = "subscriber_id", referencedColumnName = "id")
   private Subscriber subscriber;


    public Bill() {
    }

    public Bill(@NotNull Services service, @NotNull Instant startDate, @NotNull Instant endDate, @NotNull double amount, @NotNull Currency currency, Subscriber subscriber) {
        setService(service);
        setStartDate(startDate);
        setEndDate(endDate);
        setAmount(amount);
        setCurrency(currency);
        setSubscriber(subscriber);
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
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
}
