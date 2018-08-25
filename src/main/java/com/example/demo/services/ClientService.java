package com.example.demo.services;

import com.example.demo.entities.Client;

public interface ClientService {

    Client getClientByEik(String eik);
}
