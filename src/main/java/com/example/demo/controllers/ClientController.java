package com.example.demo.controllers;

import com.example.demo.data.ClientRepository;
import com.example.demo.data.RoleRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import com.example.demo.entities.User;
import com.example.demo.loads.ApiResponse;
import com.example.demo.loads.JwtAuthResponse;
import com.example.demo.loads.LoginRequest;
import com.example.demo.loads.SignUpClientRequest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.services.ClientServiceImpl;
import com.example.demo.services.RoleServiceImpl;
import com.sun.org.apache.xalan.internal.xsltc.dom.CachedNodeListIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth/client")
public class ClientController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateClient(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseEntity<?> response = null;
        try {
            response = clientService.authenticateClient(loginRequest);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@Valid @RequestBody SignUpClientRequest signUpClientRequest) {
        ResponseEntity<?> result = null;
        try {
            result = clientService.registerClient(signUpClientRequest);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


}
