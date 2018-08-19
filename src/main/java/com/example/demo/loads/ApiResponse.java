package com.example.demo.loads;

public class ApiResponse {
    private Boolean successful;

    private String message;

    public ApiResponse(Boolean successful, String message) {
        setSuccessful(successful);
        setMessage(message);
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
