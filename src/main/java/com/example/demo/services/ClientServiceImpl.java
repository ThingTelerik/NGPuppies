package com.example.demo.services;

import com.example.demo.data.ClientRepository;
import com.example.demo.data.RoleRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService, GenericService<Client> {

    private static final String INVALID_USER = "Invalid user";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Client getClientByEik(String eik) {
        return clientRepository.findByEik(eik);
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
