package com.example.demo.services;

import com.example.demo.data.RoleRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.loads.ApiResponse;
import com.example.demo.loads.SignUpRequest;
import com.example.demo.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.*;

@Service
@Transactional
public class UserServiceImplement implements UserDetailsService {
    private static final String INVALID_USER = "Invalid user";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  RoleRepository roleRepository;

    public UserServiceImplement() {
    }

    public ResponseEntity<?> register(SignUpRequest signUpRequest) {
        List<Role> roles = roleRepository.findAll();
        List<User> users = userRepository.findAll();

        if(
                //findByUsername(signUpRequest.getUsername())
                users.stream()
                .anyMatch(user -> user.getUsername().equals(signUpRequest.getUsername()))){
            return new ResponseEntity<>(new ApiResponse(false, "Username already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(users.stream()
                .anyMatch(user -> user.getEmail().equals(signUpRequest.getEmail()))){
            return new ResponseEntity<>(new ApiResponse(false, "Email already in use!"),
                    HttpStatus.BAD_REQUEST);
        }


        User userToRegister = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPhone(), signUpRequest.getPassword());

       userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));

        Role role = roles.stream()
                .filter(x->x.getName().equalsIgnoreCase("user"))
                .findFirst()
                .orElse(null);

        if(role==null){
            role = new Role();
            role.setName("user");
            roleRepository.save(role);
        }
        role.setUsers(Collections.singleton(userToRegister));
        userToRegister.setRole(role);


       User result = userRepository.save(userToRegister);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();


        return ResponseEntity.created(location).body(new ApiResponse(true, "User successfully registered"));
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> allUsers = userRepository.findAll();

        User user = allUsers.stream()
                .filter(x->x.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if(user ==null){
            throw  new UsernameNotFoundException("User not found");

        }
        Role role = user.getRole();

<<<<<<< HEAD
        Role role = user.getRole();
=======
>>>>>>> master
        Set<SimpleGrantedAuthority> granatedAuthorities =  Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.getName()));

        return CustomUserDetails.create(user);

    }


    public UserDetails loadUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(
                        NoSuchElementException::new
                );

        if(user ==null){
            throw new IllegalArgumentException("User not found by id");
        }

        return CustomUserDetails.create(user);
    }
}
