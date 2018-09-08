package com.example.demo.services;

import com.example.demo.data.ClientRepository;
import com.example.demo.data.SubscriberRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleType;
import com.example.demo.entities.Subscriber;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class SubscriberServiceTests {
    private static final int ID = 1;



    @Mock
    private SubscriberRepository mockRepository;
    @Mock
    private ClientRepository mockClientRepo;

    @InjectMocks
    SubscriberService service;

    private Subscriber subscriber;



    @Before
    public void init() {
       
    }
}
