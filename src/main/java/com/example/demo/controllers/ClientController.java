package com.example.demo.controllers;

import com.example.demo.entities.Client;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.SignUpClientRequest;
import com.example.demo.security.CurrentLoggedUser;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.services.ClientServiceImpl;
import com.example.demo.services.base.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    /**
     *
     * @param signUpClientRequest
     * @return
     * {
     * "name": "FiBank",
     * "username": "fibank",
     * "password": "123456789",
     * "eik": "5487984856"
     *
     * }
     */
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

    /**
     *
      * @param loginRequest
     * @return
     * {
     * "username": "fibank",
     * "password": "123456789"
     * }
     */
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

    /**
     *
     * @param pageable
     * @return
     *
     * get all users with client role
     *
     */
    @GetMapping("/clients")
    public Page<Client> getAllClients(Pageable pageable) {
        return clientService.getAll(pageable);
    }


    /**
     *
     * @param activeUser
     * @return
     * Get current logged user by passing the auth token in autauthorization header
     */
    @GetMapping("/currentUser")
    public Client getCurrentLoggedUser(@CurrentLoggedUser CustomUserDetails activeUser) {
        Client client = clientService.getClientByUsername(activeUser.getUsername());

        return client;
    }

    /**
     *
     * @param clientID
     * @param postRequest
     * @return
     * update client by passing: username, password and eik
     */
    @PutMapping("/clients/{clientID}")
    public Client updateClient(@PathVariable("clientID") Long clientID, @Valid @RequestBody Client postRequest) {
       return this.clientService.updateClient(clientID, postRequest);

    }

    /**
     *
     * @param postId
     * @return
     * delete client by passing the client id in the url
     */
    @DeleteMapping("/clients/{clientID}")
    public ResponseEntity<?> deleteClient(@PathVariable("clientID") Long postId) {

        return clientService.deleteClientById(postId);

    }

}
