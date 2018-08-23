package com.example.demo.data;

import com.example.demo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
public interface RoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByName (String roleName);

    @Override
    List<Role> findAll();

    @Override
    <S extends Role> S save(S entity);
}
