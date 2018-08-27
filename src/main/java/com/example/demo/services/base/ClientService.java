package com.example.demo.services.base;

import com.example.demo.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ClientService {

    Client getClientByEik(String eik);

    Boolean existsByEik(String eik);

    Boolean existsByUsername(String username);

    void deleteUserByUsername(String username);

    Client getClientByUsername(String username);

    Page<Client> getAll(Pageable pageable);


    Client updateClient(Long clientID, Client postRequest);

    ResponseEntity<?> deleteClientById(Long clientID);
}
