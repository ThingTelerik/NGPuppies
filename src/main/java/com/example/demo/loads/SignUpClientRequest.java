package com.example.demo.loads;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SignUpClientRequest extends SignUpRequest {

    @NotNull
    @Length(max = 50)
    private String eik;

    public String getEik() {
        return eik;
    }

    public void setEik(String eik) {
        this.eik = eik;
    }
}
