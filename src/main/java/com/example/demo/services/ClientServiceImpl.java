package com.example.demo.services;

import com.example.demo.data.ClientRepository;
import com.example.demo.data.RoleRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.loads.ApiResponse;
import com.example.demo.loads.JwtAuthResponse;
import com.example.demo.loads.LoginRequest;
import com.example.demo.loads.SignUpClientRequest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.services.base.ClientService;
import com.example.demo.services.base.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService, GenericService<Client, String> {

    private static final String INVALID_USER = "Invalid user";

    private ClientRepository clientRepository;


    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;


    private JwtTokenProvider tokenProvider;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
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
    public ResponseEntity<?> registerClient(@Valid @RequestBody SignUpClientRequest signUpClientRequest){
        if(clientRepository.existsByEik(signUpClientRequest.getEik())){
            return new ResponseEntity(new ApiResponse(false, "EIK already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(clientRepository.existsByUsername(signUpClientRequest.getUsername())){
            return new ResponseEntity(new ApiResponse(false, "Username is already used!"),
                    HttpStatus.BAD_REQUEST);
        }

        Client client = new Client(signUpClientRequest.getUsername(), signUpClientRequest.getPassword(), signUpClientRequest.getEik());

        client.setPassword(passwordEncoder.encode(client.getPassword()));


        Role role = roleRepository.findAll().stream()
                .filter(x->x.getRoleType().equals(RoleType.ROLE_CLIENT))
                .findFirst()
                .orElse(null);

        if (role == null) {
            role = new Role(RoleType.ROLE_CLIENT);
            roleRepository.save(role);
        }

        role.getUsers().add(client);
        client.setRole(role);

        Client savedClient = clientRepository.save(client);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/clients/ {username}")
                .buildAndExpand(savedClient.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Client successfully registered"));
    }

    public ResponseEntity<?> authenticateClient(@Valid @RequestBody LoginRequest loginRequest) {

        User u = clientRepository.findClientByUsername(loginRequest.getUsername());

        UsernamePasswordAuthenticationToken authRequest= null;
        if (u != null) {
            authRequest = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());
        }

        Authentication authentication = authenticationManager.authenticate(authRequest);


        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(jwt));

    }


}
