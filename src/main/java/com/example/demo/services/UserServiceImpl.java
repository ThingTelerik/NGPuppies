package com.example.demo.services;

import com.example.demo.data.UserRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.User;
import com.example.demo.loads.JwtAuthResponse;
import com.example.demo.model.LoginRequest;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.services.base.GenericService;
import com.example.demo.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, GenericService<User, String> {


    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider tokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(User entity) {
    userRepository.delete(entity);
    }

    @Override
    public User update(User entity, String param) {
    return userRepository.updateUserByUsername(entity, param);

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void deleteUserByUsername(String username) {
    userRepository.deleteUserByUsername(username);
    }

    @Override
    public User findUserById(Long aLong) {
        return userRepository.findUserById(aLong);
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public ResponseEntity<?> authenticateClient(LoginRequest loginRequest) {

        User u = userRepository.findUserByUsername(loginRequest.getUsername());

        UsernamePasswordAuthenticationToken authRequest = null;
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
