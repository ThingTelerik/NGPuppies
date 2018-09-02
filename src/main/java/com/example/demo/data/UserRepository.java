package com.example.demo.data;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    List<User> findAll();

    User findByUsername(String username);

    @Query("Select u from User u where u.username = ?1")
    User findUserByUsername(String username);

    @Query("Delete from User u where u.username = ?1")
    void deleteUserByUsername(String username);

    @Query("Select u from User u where u.id = ?1")
    User findUserById(Long aLong);

    @Modifying
    @Query("update User u set u = ?1 where u.username =?2")
    User updateUserByUsername(User user, String username);

    @Override
    <S extends User> S save(S entity);
}
