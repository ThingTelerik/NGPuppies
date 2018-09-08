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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasRole('CLIENT')")
@RequestMapping("/api")
public class SubscriberController {
   private ISubscriberService subscriberService;

   private ClientService clientService;

    @Autowired
    public SubscriberController(ISubscriberService subscriberService, ClientService clientService){
        this.subscriberService = subscriberService;
        this.clientService= clientService;
    }

    /**
     *
     * @param loggedUser
     * @param subscriber
     * @return
     * Create subscriber for a current Logged user
     * by passing the bearer token and example subscriber:
     * {
     * 			"phoneNumber": "08888888",
     *             "firstName": "Edward",
     *             "lastName": "Stewart",
     *             "address": "Sofia",
     *             "egn": "123456798"
     * }
     *
     */
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/clients/subscribers")
    public Subscriber createSubscriber(@CurrentLoggedUser CustomUserDetails loggedUser,
                                       @Valid @RequestBody Subscriber subscriber) {
        return this.subscriberService.createSubscriberByClientId(loggedUser.getId(), subscriber);
    }


    /**
     *
     * @param loggedUser
     * @param pageable
     * @return
     * gets all subscribers for the logged user
     */
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/clients/subscribers")
    public Page<Subscriber> getAllSubscriersByCurrentLoggedClient(@CurrentLoggedUser CustomUserDetails loggedUser,Pageable pageable) {
        return this.subscriberService.getAllSubscribersByClientsID(loggedUser.getId(),pageable);
    }

    /**
     *
     * @param loggedUser
     * @param subscriberId
     * @return
     * get subscriber by id for the current logged user
     * returns only the subscribers that belong to the current logged user
     */
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/clients/{subscriberId}")
    public Subscriber getSubscriberByID(@CurrentLoggedUser CustomUserDetails loggedUser, @PathVariable(value = "subscriberId") Integer subscriberId){
        return this.subscriberService.getSubscriberByID(loggedUser.getId(), subscriberId);
    }

    /**
     *
     * @param loggedUser
     * @param sID
     * @param subscriberRequest
     * @return
     * Updates a subscriber by a given id
     * only if the subscriber belongs to the current logged bank else throws exception
     * example for editing:
     * {
     *
     *     "phoneNumber": "78946",
     *     "firstName": "Christina",
     *     "lastName": "Aguilera",
     *     "address": "New York",
     *     "egn": "777777"
     * }
     */
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/clients/subscribers/{subscriberID}")
    public Subscriber updateSubscriber(@CurrentLoggedUser CustomUserDetails loggedUser,
                                       @PathVariable (value = "subscriberID") Integer sID,
                                       @Valid @RequestBody Subscriber subscriberRequest) {

        return this.subscriberService.updateSubscriberByClientID(loggedUser.getId(),sID,subscriberRequest);

    }

    /**
     *
     * @param loggedUser
     * @param subscriberID
     * @return
     * delete subscriber for current logged user
     * deleting subscribers which don't belong to the current user is impossible
     */
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/clients/subscribers/{subscriberID}")
    public ResponseEntity<?> deleteSubscriber(@CurrentLoggedUser CustomUserDetails loggedUser,
                                           @PathVariable (value = "subscriberID") Integer subscriberID) {

        return subscriberService.deleteSubscriberByClientID(loggedUser.getId(),subscriberID);
}

}