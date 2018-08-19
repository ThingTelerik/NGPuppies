package com.example.demo.services;

import com.example.demo.data.GenericRepository;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.SecondaryTable;
import javax.transaction.Transactional;
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
    public boolean register(User user) {
        User userToRegister = new User(user.getUsername(), user.getEmail(), user.getPhone(), user.getPassword());

        List<Role> roles = roleGenericRepository.getAll();
        List<User> users = userGenericRepository.getAll();

        Role role = roles.stream()
                .filter(x->x.getName().equalsIgnoreCase("user"))
                .findFirst()
                .orElse(null);

        if(role==null){
            role = new Role();
            role.setName("user");
            roleGenericRepository.create(role);
        }
        role.getUsers().add(user);
        user.getRoles().add(role);

        if(users.stream()
                .anyMatch(x->x.getEmail().equalsIgnoreCase(user.getEmail())|| x.getUsername().equalsIgnoreCase(user.getUsername()))){
            //TODO
            throw  new IllegalArgumentException();
        }
        userGenericRepository.create(user);
        return true;
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
        Set<SimpleGrantedAuthority> grnatedAuthorities = roles.stream()
                                                                .map(x-> new SimpleGrantedAuthority("ROLE_"+ x.getName()))
                                                                .collect(Collectors.toSet());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grnatedAuthorities
        );

        return userDetails;

    }
}
