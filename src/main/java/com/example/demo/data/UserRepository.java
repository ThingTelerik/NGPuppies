package com.example.demo.data;

import com.example.demo.entities.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();


    Optional<User> findById(Long aLong);

     Optional<User> findByUsername(String username);
}
