package com.example.demo;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.controllers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private ClientController clientController;
    @Autowired

    private AdminController adminController;
    @Autowired

    private BillController billController;
    @Autowired

    private ServicesController servicesController;
    @Autowired

    private SubscriberController subscriberController;

    @Test
    public void clientControllerLoads() throws Exception {
        assertThat(clientController).isNotNull();
    }


    @Test
    public void adminControllerLoads() throws Exception {
        assertThat(adminController).isNotNull();
    }


    @Test
    public void billControllerLoads() throws Exception {
        assertThat(billController).isNotNull();
    }


    @Test
    public void servicesControllerLoads() throws Exception {
        assertThat(servicesController).isNotNull();
    }


    @Test
    public void subscriberControllerLoads() throws Exception {
        assertThat(subscriberController).isNotNull();
    }

}