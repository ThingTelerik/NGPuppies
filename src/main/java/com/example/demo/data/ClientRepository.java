package com.example.demo.data;

import com.example.demo.entities.Client;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClientRepository extends UserRepository {
    @Override
    List<User> findAll();

    @Query("Select c from Client c where c.EIK = ?1")
    Client findByEik(String eik);




}
