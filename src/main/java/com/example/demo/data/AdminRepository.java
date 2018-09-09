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

    Admin findByEmail(String email);


    Admin findByUsername(String username);

    Boolean existsByEmail(String email);

    @Override
    void deleteById(Long aLong);

    Boolean existsByUsername(String username);

    @Override
    void delete(Admin entity);

    @Modifying
    @Query("update Admin a set a = ?1 where a.email =?2")
    void updateAdminByEmail(Admin admin, String email);

    @Override
    <S extends Admin> S save(S entity);
}
