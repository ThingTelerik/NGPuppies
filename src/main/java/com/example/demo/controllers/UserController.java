package com.example.demo.controllers;

import com.example.demo.entities.Client;
import com.example.demo.entities.User;
import com.example.demo.security.CurrentLoggedUser;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/loggedUser")
    public User getCurrentLoggedUser(@CurrentLoggedUser CustomUserDetails activeUser) {
        System.out.println("id:"+activeUser.getId());
        System.out.println("username"+activeUser.getUsername());
        return userService.findUserById(activeUser.getId());


    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }
}
