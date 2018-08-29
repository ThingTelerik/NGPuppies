package com.example.demo.services.base;

import com.example.demo.entities.Services;

import java.util.List;

public interface ServicesService {



    List<Services> findBySubscriberPhone(String phone);

    List<Services> findBySubscriberEGN(String egn);

    List<Services> getAll();

    Services createService(Services newService);

    List<Services> getAllServicesBySubscriber(Long clientId, Integer subscriberID);
}

