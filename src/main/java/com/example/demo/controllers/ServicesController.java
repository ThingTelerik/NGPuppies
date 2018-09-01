package com.example.demo.controllers;

import com.example.demo.entities.Services;
import com.example.demo.model.ServicesDto;
import com.example.demo.services.base.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    Collection<Services> getAllServices(){
        return this.servicesService.getAll();
    }

    @GetMapping("/allPaidBySub/{phone}")
    List<ServicesDto> allPaidServicesBySubscriber(@PathVariable(value = "phone") String phone){
        return servicesService.allPaidServicesBySubscriber(phone);
    }

    //Create new service
    @PostMapping("/services")
    Services createService(@RequestBody ServicesDto newService){
       return this.servicesService.createService(newService);
    }

    //Get services by Subscriber
    @GetMapping("/clients/{clientID}/subscribers/{subscriberID}/services")
    Collection<Services> getAllServicesBySubscriber(@PathVariable(value = "clientID") Long clientId, @PathVariable(value = "subscriberID") Integer subscriberID ){
        return servicesService.getAllServicesBySubscriber(clientId,subscriberID);
    }

    //Add service by SubscriberID
    @PostMapping("/clients/{clientID}/subscribers/{subscriberID}/services")
    Services createServiceBySubscriberID(@PathVariable(value = "clientID") Long clientId, @PathVariable(value = "subscriberID") Integer subscriberId,Services newService){
        return servicesService.createServiceBySubscriberID(clientId,subscriberId,newService);
    }

    @GetMapping("/services/{name}")
    Services getByName(@PathVariable (value = "name") String name){
        return this.servicesService.getByName(name);
    }
 }
