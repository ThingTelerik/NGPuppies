package com.example.demo.services;

import com.example.demo.data.AdminRepository;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

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

    @Test(expected = NullPointerException.class)
    public void passNullToCreateNewAdminMethod_AssertNoAdminIsCreated(){
        Admin missing = null;

        service.create(missing);

        Assert.assertNull(mockRepository.findById(missing.getId()));
    }

    @Test
    public void getAdminByEmailMethodCalled_returnAdminByEmail(){
        Mockito.when(mockRepository.findByEmail("admin@abv.bg")).thenReturn(admin);

       Admin currentAdmin= service.getAdminByEmail("admin@abv.bg");

       Assert.assertEquals(admin,currentAdmin);
    }


    @Test
    public void repoContains3elements_getAll_return3elements() {
        // Data preparation
        List<Admin> users = Arrays.asList(admin, admin, admin);
        Mockito.when(mockRepository.findAll()).thenReturn(users);

        // Method call
        List<Admin> userList = service.getAll();

        // Verification
        Assert.assertThat(userList, Matchers.hasSize(3));
        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(mockRepository);

    }




}
