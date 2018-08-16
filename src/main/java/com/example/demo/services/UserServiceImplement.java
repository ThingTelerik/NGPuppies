package com.example.demo.services;

import com.example.demo.data.GenericRepository;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        return null;
    }

    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
