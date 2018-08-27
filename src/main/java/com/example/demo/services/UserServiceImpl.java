package com.example.demo.services;

import com.example.demo.data.UserRepository;
import com.example.demo.entities.User;
import com.example.demo.services.base.GenericService;
import com.example.demo.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, GenericService<User, String> {

    @Autowired
    private UserRepository userRepository;

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
    public void update(User entity, String param) {
    userRepository.updateUserByUsername(entity, param);
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
}
