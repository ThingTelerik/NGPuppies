package com.example.demo.controllers;

import com.example.demo.data.ClientRepository;
import com.example.demo.data.RoleRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.loads.ApiResponse;
import com.example.demo.loads.JwtAuthResponse;
import com.example.demo.loads.LoginRequest;
import com.example.demo.loads.SignUpClientRequest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.services.ClientServiceImpl;
import com.example.demo.services.RoleServiceImpl;
import com.sun.org.apache.xalan.internal.xsltc.dom.CachedNodeListIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    //TODO fix
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

    //Register new client
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

    //TODO METHOD GETTING BANK BY ID



    //Get all clients
    @GetMapping("/clients")
    public Page<Client> getAllClients(Pageable pageable) {
        return clientService.getAll(pageable);
    }

    //TODO POJO CLIENT CLASS
    //update client by id
    @PutMapping("/clients/{clientID}")
    public Client updateClient(@PathVariable("clientID") Long clientID, @Valid @RequestBody Client postRequest) {
       return this.clientService.updateClient(clientID, postRequest);

    }

    //delete cient by id
    @DeleteMapping("/clients/{clientID}")
    public ResponseEntity<?> deleteClient(@PathVariable("clientID") Long postId) {

        return clientService.deleteClientById(postId);

    }






}
