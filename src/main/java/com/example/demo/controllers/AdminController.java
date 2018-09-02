package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.SignUpClientRequest;
import com.example.demo.model.SignupAdminRequest;
import com.example.demo.services.AdminServiceImpl;
import com.example.demo.services.ClientServiceImpl;
import com.example.demo.services.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    /**
     *
     * @param signUpAdminRequest
     * @return
     * {
     * "name": "xxxxxx",
     * "username": "xxxxx",
     * "password" :"xxxxxxx",
     * "email":"xxxxx@xxx.xx"
     * }
     */
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

    /**
     *
     * @param loginRequest
     * @return
     *
     * {
     * "username": "xxxxxxxx",
     * "password" :"xxxxxx"
     *
     * }
     */
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


    /**
     *
     * @return
     *  returns all admins
     */
    @GetMapping(value = "all")
    public ResponseEntity<List<User>> getAllArticles() {
        List<User> responseUserList = new ArrayList<>();
        List<User> usersList = userService.getAll();
        for (int i = 0; i < usersList.size(); i++) {
            User user = new User();
            BeanUtils.copyProperties(usersList.get(i), user);
            user.setId(usersList.get(i).getId());
            responseUserList.add(user);
        }
        return new ResponseEntity<List<User>>(responseUserList, HttpStatus.OK);
    }


    //curl http://localhost:8080/api/auth/admin/deleteClient/{username)
    //needs better implementation maybe
    @DeleteMapping("/deleteClient/{username}")
    public ResponseEntity deleteClient(@PathVariable("username") String username) {
      return   adminService.deleteClient(username);
    }


    //Does not work
    //Transient instance bullshit -> cascade type to fix\
    //TODO FIX
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

    @PutMapping("/update/{email}")
    public Admin updateAdmin(@PathVariable("email") String email, @Valid @RequestBody Admin admin) {

        return adminService.update(admin, email);

    }


}
