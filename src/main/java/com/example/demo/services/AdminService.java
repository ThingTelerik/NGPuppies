package com.example.demo.services;

import com.example.demo.entities.Admin;

public interface AdminService {

    Admin getAdminByEmail(String email);
}
