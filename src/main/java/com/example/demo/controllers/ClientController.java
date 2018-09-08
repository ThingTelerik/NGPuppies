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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
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
     * @param pageable
     * @return
     *
     * get all users with client role
     *
     */
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/clients/{clientID}")
    public ResponseEntity<?> deleteClient(@PathVariable("clientID") Long postId) {

        return clientService.deleteClientById(postId);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("clients/{clientId}")
    public Client getClientByID(@PathVariable(value = "clientId") Long clientId){
        return this.clientService.getClientById(clientId);
    }


}
