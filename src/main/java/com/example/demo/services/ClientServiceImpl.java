package com.example.demo.services;

import com.example.demo.data.ClientRepository;
import com.example.demo.entities.Client;
import com.example.demo.services.base.ClientService;
import com.example.demo.services.base.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService, GenericService<Client, String> {

    private static final String INVALID_USER = "Invalid user";


    @Autowired
    private ClientRepository clientRepository;


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
    public Client getClientByUsername(String username) {
        return clientRepository.findClientByUsername(username);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client create(Client entity) {
        return clientRepository.save(entity);
    }

    @Override
    public void delete(Client entity) {
    clientRepository.delete(entity);
    }

    @Override
    public void update(Client entity, String eik) {
    clientRepository.updateClientByEik(entity, eik);
    }

}
