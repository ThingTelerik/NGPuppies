package com.example.demo.loads;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SignUpRequest {

    @NotNull
    @Length(min = 3, max = 30)
    private String name;

    @NotNull
    @Length(min =3, max = 20)
    private String username;

    @NotNull
    @Length(max = 30)
    @Email
    private String email;

    @NotNull
    @Length(max =12)
    private String phone;

    @NotNull
    @Length(min =7)
    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
