package com.example.demo.services.base;

import com.example.demo.entities.User;

public interface UserService {

    User findByUsername(String username);

    void deleteUserByUsername(String username);

    User findUserById(Long aLong);





}
