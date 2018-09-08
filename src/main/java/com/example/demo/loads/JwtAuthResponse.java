package com.example.demo.loads;

import com.example.demo.entities.Role;

public class JwtAuthResponse {
     private String accessToken;
     private String tokenType = "Bearer";
     private String role;

    public JwtAuthResponse(String accessToken, String role) {
        setAccessToken(accessToken);
        setRole(role);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
