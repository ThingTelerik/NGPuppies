package com.example.demo.controllers;

import com.example.demo.data.ClientRepository;
import com.example.demo.data.RoleRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import com.example.demo.loads.ApiResponse;
import com.example.demo.loads.JwtAuthResponse;
import com.example.demo.loads.LoginRequest;
import com.example.demo.loads.SignUpClientRequest;
import com.example.demo.security.JwtTokenProvider;
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

@RestController
@RequestMapping("/api/auth/client")
public class ClientController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateClient(@Valid @RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@Valid @RequestBody SignUpClientRequest signUpClientRequest){
        if(clientRepository.existsByEik(signUpClientRequest.getEik())){
            return new ResponseEntity(new ApiResponse(false, "EIK already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        Client client = new Client(signUpClientRequest.getUsername(), signUpClientRequest.getPassword(), signUpClientRequest.getEik());

        client.setPassword(passwordEncoder.encode(client.getPassword()));


        RoleType clientRole = client.getRole().getRoleType();

        Role role = new Role(clientRole);

        if(!(roleRepository.existsByRoleType(clientRole))){
            roleRepository.save(role);
        }


        Client savedClient = clientRepository.save(client);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/clients/ {username}")
                .buildAndExpand(savedClient.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Client successfully registered"));
    }


}
