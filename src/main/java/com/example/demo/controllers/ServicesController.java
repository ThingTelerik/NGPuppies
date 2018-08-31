package com.example.demo.controllers;

import com.example.demo.entities.Services;
import com.example.demo.services.base.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ServicesController {
    ServicesService servicesService;

    @Autowired
    public ServicesController(ServicesService servicesService){
        this.servicesService =servicesService;
    }
    //Get all services
    @GetMapping("/services")
    List<Services> getAllServices(){
        return this.servicesService.getAll();
    }

    @GetMapping("/allPaidBySub/{phone}")
    List<Services> allPaidServicesBySubscriber(@PathVariable(value = "phone") String phone){
        return servicesService.allPaidServicesBySubscriber(phone);
    }

    //Create new service
    @PostMapping("/services")
    Services createService(@RequestBody Services newService){
       return this.servicesService.createService(newService);
    }

    //Get services by Subscriber
    @GetMapping("/clients/{clientID}/subscribers/{subscriberID}/services")
    List<Services> getAllServicesBySubscriber(@PathVariable(value = "clientID") Long clientId, @PathVariable(value = "subscriberID") Integer subscriberID ){
        return servicesService.getAllServicesBySubscriber(clientId,subscriberID);
    }

    //Add service by SubscriberID
    @PostMapping("/clients/{clientID}/subscribers/{subscriberID}/services")
    Services createServiceBySubscriberID(@PathVariable(value = "clientID") Long clientId, @PathVariable(value = "subscriberID") Integer subscriberId,Services newService){
        return servicesService.createServiceBySubscriberID(clientId,subscriberId,newService);
    }
 }
