package com.example.demo.services;

import com.example.demo.data.AdminRepository;
import com.example.demo.data.ClientRepository;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Client;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTests {

    private static final long ADMIN_ONE_ID = 1L;
    private static final long MISSING_ID= 2L;


    @Mock
    private AdminRepository mockRepository;

    @InjectMocks
    AdminServiceImpl service;

    private Admin admin;



    @Before
    public void init() {
        admin = new Admin();
        admin.setId(ADMIN_ONE_ID);
        admin.setUsername("admin1");
        admin.setPassword("123456");
        admin.setEmail("admin@abv.bg");

        admin.setRole(new Role(RoleType.ROLE_ADMIN));


    }

    @Test
    public void createNewAdmin_newAdminIsSavedToTheRepository(){

        Admin newAdmin = new Admin("user","123456","user@abv.bg");

        service.create(newAdmin);

        Assert.assertNotNull(mockRepository.findById(newAdmin.getId()));
    }


}
