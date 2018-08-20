package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.loads.SignUpRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<User> findAll();

    ResponseEntity<?> register(SignUpRequest signUpRequest);

    User findByUsername(String username);

    User findByEmail(String email);



}
