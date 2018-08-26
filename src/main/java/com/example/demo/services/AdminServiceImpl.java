package com.example.demo.services;

import com.example.demo.data.AdminRepository;
import com.example.demo.entities.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements GenericService<Admin, String>, AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
       return adminRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin create(Admin entity) {
        return adminRepository.save(entity);
    }

    @Override
    public void delete(Admin entity) {
        adminRepository.delete(entity);
    }

    @Override
    public void update(Admin entity, String param) {
        adminRepository.updateAdminByEmail(entity, param);
    }
}
