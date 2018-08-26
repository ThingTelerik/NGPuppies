package com.example.demo.services;

import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;

public interface RoleService {

    Role findByName (RoleType roleName);

    Boolean existsByRoleType(RoleType roleType);
}
