package com.example.demo.services;

import com.example.demo.data.GenericRepository;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.loads.ApiResponse;
import com.example.demo.loads.SignUpRequest;
import com.example.demo.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.SecondaryTable;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImplement implements UserService, UserDetailsService {
    private static final String INVALID_USER = "Invalid user";
    private PasswordEncoder passwordEncoder;
    private final GenericRepository<User> userGenericRepository;
    private final  GenericRepository<Role> roleGenericRepository;

    @Autowired
    public UserServiceImplement(PasswordEncoder passwordEncoder, @Qualifier("User") GenericRepository<User> userGenericRepository, @Qualifier("Role") GenericRepository<Role> roleGenericRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userGenericRepository = userGenericRepository;
        this.roleGenericRepository = roleGenericRepository;
    }

    @Override
    public List<User> findAll() {
        return userGenericRepository.getAll();
    }

    @Override
    public ResponseEntity<?> register(SignUpRequest signUpRequest) {
        List<Role> roles = roleGenericRepository.getAll();
        List<User> users = userGenericRepository.getAll();

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
            roleGenericRepository.create(role);
        }
        role.getUsers().add(userToRegister);
        userToRegister.getRoles().add(role);


        userGenericRepository.create(userToRegister);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("api/users/{username}")
                .buildAndExpand(userToRegister.getUsername()).toUri();


        return ResponseEntity.created(location).body(new ApiResponse(true, "User successfully registered"));
    }



    @Override
    public User findByUsername(String username) {

        return userGenericRepository.getAll()
                .stream()
                .filter(x->x.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userGenericRepository.getAll()
                .stream()
                .filter(x->x.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> allUsers = userGenericRepository.getAll();

        User user = allUsers.stream()
                .filter(x->x.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if(user ==null){
            throw  new UsernameNotFoundException("User not found");

        }

        Set<Role> roles = user.getRoles();
        Set<SimpleGrantedAuthority> granatedAuthorities = roles.stream()
                                                                .map(x-> new SimpleGrantedAuthority("ROLE_"+ x.getName()))
                                                                .collect(Collectors.toSet());

        return CustomUserDetails.create(user);

    }


    public UserDetails loadUserById(Long id){
        User user = userGenericRepository.getById(id);

        if(user ==null){
            throw new IllegalArgumentException("User not found by id");
        }

        return CustomUserDetails.create(user);
    }
}
