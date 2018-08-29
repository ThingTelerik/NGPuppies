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

    //Create new service
    @PostMapping("/services")
    Services createService(@RequestBody Services newService){
       return this.servicesService.createService(newService);
    }
}
