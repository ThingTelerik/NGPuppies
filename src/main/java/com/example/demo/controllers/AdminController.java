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

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateClient(@Valid @RequestBody LoginRequest loginRequest) {


        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword());


        Authentication authentication = authenticationManager.authenticate(authRequest);


        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(jwt));

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupAdminRequest signUpAdminRequest) {
        if (adminService.existsByEmail(signUpAdminRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (adminService.existsByUsername(signUpAdminRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already used!"),
                    HttpStatus.BAD_REQUEST);
        }

        Admin admin = new Admin(signUpAdminRequest.getUsername(), signUpAdminRequest.getPassword(), signUpAdminRequest.getEmail());

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));


        Role role = roleService.getAll().stream()
                .filter(x -> x.getRoleType().equals(RoleType.ROLE_ADMIN))
                .findFirst()
                .orElse(null);

        if (role == null) {
            role = new Role(RoleType.ROLE_ADMIN);
            roleService.create(role);
        }

        role.getUsers().add(admin);
        admin.setRole(role);

        Admin saveAdmin = adminService.create(admin);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/admins/ {username}")
                .buildAndExpand(saveAdmin.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Client successfully registered"));
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
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {


        clientService.update(client, client.getEIK());

        Client result = new Client();
        BeanUtils.copyProperties(client, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //curl http://localhost:8080/api/auth/admin/deleteClient/{username)
    //needs better implementation maybe
    @DeleteMapping("/deleteClient/{username}")
    public ResponseEntity deleteClient(@PathVariable("username") String username) {
        if(clientService.existsByUsername(username)){
            clientService.deleteUserByUsername(username);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

    }


}
