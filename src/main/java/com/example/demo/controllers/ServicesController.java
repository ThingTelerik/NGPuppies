package com.example.demo.controllers;

import com.example.demo.entities.Services;
import com.example.demo.model.ServicesDto;
import com.example.demo.security.CurrentLoggedUser;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.services.base.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * @param newService
     * @return
     * creating service
     * example:
     * {     *
     *   "name":"Telephone"
     * }
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/services")
    Services createService(@RequestBody ServicesDto newService){
        return this.servicesService.createService(newService);
    }

    /**
     *
     * @return
     * returns all services
     */
    @GetMapping("/services")
    Collection<Services> getAllServices(){
        return this.servicesService.getAll();
    }

    /**
     *
     * @param loggedUser
     * @param subscriberId
     * @param newService
     * @return
     *
     * Add service for a given subscriber that belongs to the current logged Bank
     * example:
     * {     *
     * 	"name": "Telephone"
     * }
     * It is not possible if the passed subscriber doesn't belong to the logged user
     */
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/clients/subscribers/{subscriberID}/services")
    Services createServiceBySubscriberID(@CurrentLoggedUser CustomUserDetails loggedUser, @PathVariable(value = "subscriberID") Integer subscriberId,@RequestBody Services newService){
        return servicesService.createServiceBySubscriberID(loggedUser.getId(),subscriberId,newService);
    }

    /**
     *
     * @param loggedUser
     * @param subscriberID
     * @return
     * Get all services for a subscriber of the current logged user
     */
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/clients/subscribers/{subscriberID}/services")
    Collection<Services> getAllServicesBySubscriber(@CurrentLoggedUser CustomUserDetails loggedUser, @PathVariable(value = "subscriberID") Integer subscriberID ){
        return servicesService.getAllServicesBySubscriber(loggedUser.getId(),subscriberID);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/allPaidBySub/{phone}")
    List<ServicesDto> allPaidServicesBySubscriber(@PathVariable(value = "phone") String phone){
        return servicesService.allPaidServicesBySubscriber(phone);
    }

    @GetMapping("/services/{name}")
    Services getByName(@PathVariable (value = "name") String name){
        return this.servicesService.getByName(name);
    }
 }
