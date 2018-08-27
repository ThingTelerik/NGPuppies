package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.loads.*;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.services.AdminServiceImpl;
import com.example.demo.services.ClientServiceImpl;
import com.example.demo.services.RoleServiceImpl;
import com.example.demo.services.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/auth/admin")
public class AdminController {

    private AdminServiceImpl adminService;

    private UserServiceImpl userService;

    private ClientServiceImpl clientService;

    @Autowired
    public AdminController(AdminServiceImpl adminService, UserServiceImpl userService, ClientServiceImpl clientService) {
        this.adminService = adminService;
        this.userService = userService;
        this.clientService= clientService;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateClient(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseEntity<?> response = null;
        try {
            response = adminService.authenticateClient(loginRequest);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@Valid @RequestBody SignupAdminRequest signUpAdminRequest) {
        ResponseEntity<?> result = null;
        try {
            result = adminService.registerAdmin(signUpAdminRequest);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<User>> getAllArticles() {
        List<User> responseUserList = new ArrayList<>();
        List<User> usersList = userService.getAll();
        for (int i = 0; i < usersList.size(); i++) {
            User user = new User();
            BeanUtils.copyProperties(usersList.get(i), user);
            responseUserList.add(user);
        }
        return new ResponseEntity<List<User>>(responseUserList, HttpStatus.OK);
    }

    //Does not work
    //Transient instance bullshit -> cascade type to fix
    @PutMapping(value = "updateClientEik")
    public ResponseEntity<Client> updateClient(@Valid @RequestBody SignUpClientRequest signUpClientRequest) {
        Client client = clientService.getClientByUsername(signUpClientRequest.getUsername());
        if (client != null) {
            clientService.update(client,client.getUsername());
        }


        Client result = new Client();
        BeanUtils.copyProperties(client, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //curl http://localhost:8080/api/auth/admin/deleteClient/{username)
    //needs better implementation maybe
    @DeleteMapping("/deleteClient/{username}")
    public ResponseEntity deleteClient(@PathVariable("username") String username) {
        if (clientService.existsByUsername(username)) {
            clientService.deleteUserByUsername(username);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

    }


}
