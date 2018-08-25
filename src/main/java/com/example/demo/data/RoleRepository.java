package com.example.demo.data;

import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
public interface RoleRepository extends JpaRepository<Role, Long> {

   @Query("SELECT r from Role r where r.roleType = ?1")
   Role findByName (RoleType roleName);

    @Override
    List<Role> findAll();

    @Override
    <S extends Role> S save(S entity);
}
