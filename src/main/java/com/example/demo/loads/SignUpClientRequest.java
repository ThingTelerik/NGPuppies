package com.example.demo.loads;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SignUpClientRequest {
    @NotNull
    @Length(min = 3, max = 30)
    private String name;

    @NotNull
    @Length(min = 3, max = 20)
    private String username;

    @NotNull
    @Length(min = 6)
    private String password;

    @NotNull
    @Length(max = 50)
    private String eik;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEik() {
        return eik;
    }

    public void setEik(String eik) {
        this.eik = eik;
    }
}
