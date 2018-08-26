package com.example.demo.services;

import com.example.demo.data.RoleRepository;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements GenericService<Role, RoleType>, RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role create(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public void delete(Role entity) {
        roleRepository.delete(entity);
    }

    @Override
    public void update(Role entity, RoleType param) {
    roleRepository.updateRoleByRoleType(entity,param );
    }

    @Override
    public Role findByName(RoleType roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public Boolean existsByRoleType(RoleType roleType) {
        return null;
    }
}
