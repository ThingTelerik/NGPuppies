package com.example.demo.controllers;

import com.example.demo.data.SubscriberRepository;
import com.example.demo.entities.Subscriber;
import com.example.demo.exceptions.ResourceNotFoundException;
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

    @Autowired
    public SubscriberController(ISubscriberService subscriberService){
        this.subscriberService = subscriberService;
    }

    //create subscriber for client id
    @PostMapping("/clients/{clientID}/subscribers")
    public Subscriber createSubscriber(@PathVariable (value = "clientID") Long clientID,
                                       @Valid @RequestBody Subscriber subscriber) {
        return this.subscriberService.createSubscriberByClientId(clientID, subscriber);
    }

    //get all subscribers by client id
    @GetMapping("/clients/{clientID}/subscribers")
    public Page<Subscriber> getAllSubscriersByClientId(@PathVariable (value = "clientID") Long clientID,
                                                       Pageable pageable) {
        return subscriberService.getAllSubscribersByClientsID(clientID,pageable);
    }

    //get subscriber by id for a client
    @GetMapping("/clients/{clientID}/{subscriberId}")
    public Subscriber getSubscriberByID(@PathVariable (value = "clientID") Long clientID, @PathVariable(value = "subscriberId") Integer subscriberId){
        return this.subscriberService.getSubscriberByID(clientID, subscriberId);
    }

    //update subscriber by id for a given cient(by id)
    @PutMapping("/clients/{clientID}/subscribers/{subscriberID}")
    public Subscriber updateSubscriber(@PathVariable (value = "clientID") Long clientID,
                                       @PathVariable (value = "subscriberID") Integer sID,
                                       @Valid @RequestBody Subscriber subscriberRequest) {

        return this.subscriberService.updateSubscriberByClientID(clientID,sID,subscriberRequest);

    }

    //delete subscriber for a given client
    @DeleteMapping("/clients/{clientID}/subscribers/{subscriberID}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "clientID") Long clientID,
                                           @PathVariable (value = "subscriberID") Integer subscriberID) {

        return subscriberService.deleteSubscriberByClientID(clientID,subscriberID);



}

    //TODO GET SUBSCRIBER BY PHONENUMBER




}