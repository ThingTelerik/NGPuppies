package com.example.demo.data;

import com.example.demo.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Override
    List<Admin> findAll();

    @Query("Select a from Admin a where a.email = ?1")
    Admin findByEmail(String email);

    @Query("Select a from Admin a where a.username = ?1")
    Admin findByUserName(String username);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Admin a WHERE a.email = ?1")
    Boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Admin a WHERE a.username = ?1")
    Boolean existsByUsername(String username);

    @Override
    void delete(Admin entity);

    @Modifying
    @Query("update Admin a set a = ?1 where a.email =?2")
    void updateAdminByEmail(Admin admin, String email);

    @Override
    <S extends Admin> S save(S entity);
}
