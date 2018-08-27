package com.example.demo.controllers;

import com.example.demo.data.SubscriberRepository;
import com.example.demo.entities.Subscriber;
import com.example.demo.services.base.ISubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SubscriberController {
    ISubscriberService subscriberService;

    @Autowired
    public SubscriberController(ISubscriberService subscriberService){
        this.subscriberService = subscriberService;
    }

    //Get all subscribers
    @GetMapping("/subscribers")
    public List<Subscriber> getAllSubscribers() {
        return subscriberService.getAll();
    }

    //Get subscriber by id
    @GetMapping("/subscribers/{subscriberID}")
    public Subscriber getSubscriberById(@PathVariable (value = "subscriberID") int id){
        return this.subscriberService.getSubscriberByID(id);
    }

    //Post new Subscriber
    @PostMapping("/subscribers")
    public Subscriber createSubscriber(@Valid @RequestBody Subscriber newSubscriber){
        return this.subscriberService.createSubscriber(newSubscriber);
    }

    //Delete given Subscriber by id
    @DeleteMapping("/subscribers/{id}")
    public ResponseEntity<?> deleteSubscriber(@PathVariable(value = "id") int id) {

        return this.subscriberService.deleteSubscriber(id);
    }

    //Update given Subscriber by id
    @PutMapping("/subscribers/{id}")
    public Subscriber updateSubscriber(@PathVariable(value = "id") int id, @Valid @RequestBody Subscriber subscriber){
        return subscriberService.updateSubscriber(id, subscriber);
    }

}