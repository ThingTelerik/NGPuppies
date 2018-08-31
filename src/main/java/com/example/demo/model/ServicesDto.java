package com.example.demo.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class ServicesDto {
    @NotNull
    @Length(max = 40)
    private String name;

    public ServicesDto(@NotNull @Length(max = 40) String name) {
      setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
