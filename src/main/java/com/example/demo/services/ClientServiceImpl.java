package com.example.demo.services;

import com.example.demo.data.ClientRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientServiceImpl implements ClientService, GenericService<User> {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client getClientByEik(String eik) {
        return clientRepository.findByEik(eik);
    }

    @Override
    public List<User> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public User create(User entity) {
        return clientRepository.save(entity);
    }
}
