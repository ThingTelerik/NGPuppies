package com.example.demo.services;

import com.example.demo.data.ClientRepository;
import com.example.demo.data.RoleRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import com.example.demo.entities.User;
import com.example.demo.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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
