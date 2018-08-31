package com.example.demo.services;

import com.example.demo.data.UserRepository;
import com.example.demo.entities.User;
import com.example.demo.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    private static final String INVALID_USER = "Invalid user";

    @Autowired
    UserRepository userRepository;


    public MyUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username);

        if(user ==null){
            throw  new UsernameNotFoundException("User not found");

        }

        return CustomUserDetails.create(user);

    }

    public UserDetails loadUserById(Long id){
        User user = userRepository.findUserById(id);


        if(user ==null){
            throw new IllegalArgumentException("User not found by id");
        }

        return CustomUserDetails.create(user);
    }
}
