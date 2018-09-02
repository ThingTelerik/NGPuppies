package com.example.demo.controllers;

import com.example.demo.data.SubscriberRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Subscriber;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.security.CurrentLoggedUser;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.services.base.ClientService;
import com.example.demo.services.base.ISubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class SubscriberController {
   private ISubscriberService subscriberService;

   private ClientService clientService;

    @Autowired
    public SubscriberController(ISubscriberService subscriberService, ClientService clientService){
        this.subscriberService = subscriberService;
        this.clientService= clientService;
    }

    //create subscriber for client id
    @PostMapping("/clients/{clientID}/subscribers")
    public Subscriber createSubscriber(@PathVariable (value = "clientID") Long clientID,
                                       @Valid @RequestBody Subscriber subscriber) {
        return this.subscriberService.createSubscriberByClientId(clientID, subscriber);
    }


    //works!
    @GetMapping("/clients/subscribers")
    public Page<Subscriber> getAllSubscriersByCurrentLoggedClient(@CurrentLoggedUser CustomUserDetails loggedUser,Pageable pageable) {
        return this.subscriberService.getAllSubscribersByClientsID(loggedUser.getId(),pageable);
    }

    //get subscriber by id for a client
    @GetMapping("/clients/{subscriberId}")
    public Subscriber getSubscriberByID(@CurrentLoggedUser CustomUserDetails loggedUser, @PathVariable(value = "subscriberId") Integer subscriberId){
        return this.subscriberService.getSubscriberByID(loggedUser.getId(), subscriberId);
    }

    //update subscriber by id for a given cient(by id)
    @PutMapping("/clients/subscribers/{subscriberID}")
    public Subscriber updateSubscriber(@CurrentLoggedUser CustomUserDetails loggedUser,
                                       @PathVariable (value = "subscriberID") Integer sID,
                                       @Valid @RequestBody Subscriber subscriberRequest) {

        return this.subscriberService.updateSubscriberByClientID(loggedUser.getId(),sID,subscriberRequest);

    }

    //delete subscriber for a given client
    @DeleteMapping("/clients/subscribers/{subscriberID}")
    public ResponseEntity<?> deleteComment(@CurrentLoggedUser CustomUserDetails loggedUser,
                                           @PathVariable (value = "subscriberID") Integer subscriberID) {

        return subscriberService.deleteSubscriberByClientID(loggedUser.getId(),subscriberID);



}

    //TODO GET SUBSCRIBER BY PHONENUMBER




}