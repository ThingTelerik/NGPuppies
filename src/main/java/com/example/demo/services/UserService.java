package com.example.demo.services;

import com.example.demo.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    boolean register(User user);

    User findByUsername(String username);



}
