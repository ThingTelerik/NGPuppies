package com.example.demo.services.base;

import com.example.demo.entities.User;
import com.example.demo.loads.SignUpRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    void deleteUserByUsername(String username);

    User findUserById(Long aLong);





}
