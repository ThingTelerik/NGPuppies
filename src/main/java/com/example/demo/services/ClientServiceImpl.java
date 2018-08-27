package com.example.demo.services;

import com.example.demo.data.ClientRepository;
import com.example.demo.entities.Client;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.base.ClientService;
import com.example.demo.services.base.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService, GenericService<Client, String> {

    private static final String INVALID_USER = "Invalid user";

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }


    @Override
    public Client getClientByEik(String eik) {
        return clientRepository.findByEik(eik);
    }

    @Override
    public Boolean existsByEik(String eik) {
        return clientRepository.existsByEik(eik);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    @Override
    public void deleteUserByUsername(String username) {
        clientRepository.deleteUserByUsername(username);
    }

    @Override
    public Client getClientByUsername(String username) {
        return clientRepository.findClientByUsername(username);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }
    @Override
    public void delete(Client entity) {
    clientRepository.delete(entity);
    }

    @Override
    public void update(String entity, String eik) {
    clientRepository.updateClientByEik(entity, eik);
    }

    //create client
    @Override
    public Client create(Client entity) {
        return clientRepository.save(entity);
    }

    //Get all clients per page
    @Override
    public Page<Client> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    //update cient
    @Override
    public Client updateClient(Long clientID, Client postRequest) {
        return clientRepository.findById(clientID).map(post -> {
            post.setUsername(postRequest.getUsername());
            post.setPassword(postRequest.getPassword());
            post.setEIK(postRequest.getEIK());

            return clientRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("Client", "id", clientID));

    }

    //delete client by given id
    @Override
    public ResponseEntity<?> deleteClientById(Long clientID) {
        return clientRepository.findById(clientID).map(client -> {
            clientRepository.delete(client);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Client", "id", clientID));
    }


}
