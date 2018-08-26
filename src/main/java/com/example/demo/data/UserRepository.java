package com.example.demo.data;

import com.example.demo.entities.Client;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    List<User> findAll();

    @Query("Select u from User u where u.username = ?1")
    User findUserByUsername(String username);

    @Query("Delete from User u where u.username = ?1")
    void deleteUserByUsername(String username);

    @Query("Select u from User u where u.id = ?1")
    User findUserById(Long aLong);

    @Override
    <S extends User> S save(S entity);
}
