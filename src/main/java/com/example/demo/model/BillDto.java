package com.example.demo.model;

import com.example.demo.entities.Currency;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class BillDto {
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;


    @NotNull
    private double amount;

    @NotNull
    private Currency currency;

    public BillDto() {
    }

    public BillDto(@NotNull LocalDate startDate, @NotNull LocalDate endDate, @NotNull double amount, @NotNull Currency currency) {
        setStartDate(startDate);
        setEndDate(endDate);
        setAmount(amount);
        setCurrency(currency);
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
}
