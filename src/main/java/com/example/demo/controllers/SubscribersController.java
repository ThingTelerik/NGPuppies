package com.example.demo.controllers;

import com.example.demo.data.SubscribersRepository;
import com.example.demo.entities.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/subscribers")
public class SubscribersController {

    SubscribersRepository subscribersRepository;

    @Autowired
    public SubscribersController(SubscribersRepository subscribersRepository){
        subscribersRepository = subscribersRepository;
    }

    @GetMapping("/all")
    public List<Subscriber> getAllSubscribers(){

        return this.subscribersRepository.findAll();
    }

    @PostMapping("/subscribers")
    public Subscriber createNote(@Valid @RequestBody Subscriber subscriber) {
        return subscribersRepository.save(subscriber);
    }
}
