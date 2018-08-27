package com.example.demo.services.base;

import com.example.demo.entities.Client;

public interface ClientService {

    Client getClientByEik(String eik);

    Boolean existsByEik(String eik);

    Boolean existsByUsername(String username);

    void deleteUserByUsername(String username);

    Client getClientByUsername(String username);
}
