package com.example.demo.services;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.data.ClientRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTests {

    private static final long BANK_ONE_ID = 1L;

    @Mock
    private ClientRepository mockClientRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    private Client bank;



    @Before
    public void init() {
        bank = new Client();
        bank.setId(BANK_ONE_ID);
        bank.setUsername("bank1");
        bank.setPassword("123456");
        bank.setEIK("12346");
        bank.setRole(new Role(RoleType.ROLE_CLIENT));


    }

    @Test
    public void findOneAndUserExists() {
        // Data preparation
        Mockito.when(mockClientRepository.findById(BANK_ONE_ID)).thenReturn(Optional.of(bank));

        // Method call
        Client user = clientService.getClientById(BANK_ONE_ID);

        // Verification
        Assert.assertNotNull(user);
        Mockito.verify(mockClientRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(mockClientRepository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findOneAndUserIsNull() {
        // Method call
        Client user = clientService.getClientById(BANK_ONE_ID);

        // Verification
        Assert.assertNull(user);
        Mockito.verify(mockClientRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(mockClientRepository);
    }

    @Test
    public void createBankWithExistingEik_bankIsNull() {
        // Data preparation
        Mockito.when(mockClientRepository.findByEik("12346")).thenReturn(bank);

        // Method call
        Client user = clientService.create(bank);

        // Verification
        Assert.assertNull(user);


    }

    @Test
    public void createBankWithExistingUserName_bankShouldBeNull() {
        // Data preparation
        Mockito.when(mockClientRepository.findByUsername("bank1")).thenReturn(bank);

        // Method call
        Client user = clientService.create(bank);

        // Verification
        Assert.assertNull(user);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateBank_BankNameAndEIKAndPasswordAreUpdated() {
        Client updatableBank = new Client("bank2","789465","445678");


        // Method call
        clientService.updateClient(BANK_ONE_ID,updatableBank);

        // Verification
        Assert.assertEquals(updatableBank.getEIK(),bank.getEIK());
        Assert.assertEquals(updatableBank.getPassword(),bank.getPassword());
        Assert.assertEquals(updatableBank.getUsername(),bank.getUsername());
        Mockito.verify(mockClientRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(mockClientRepository);
    }

}
