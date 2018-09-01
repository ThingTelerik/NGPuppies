package com.example.demo.services.base;

import com.example.demo.entities.Services;
import com.example.demo.model.ServicesDto;

import java.util.List;

public interface ServicesService {
    Services getById( Long servicesId);

    List<ServicesDto> allPaidServicesBySubscriber(String phone);

    List<Services> findBySubscriberPhone(String phone);

    List<Services> findBySubscriberEGN(String egn);

    List<Services> getAll();

    Services createService(ServicesDto newService);

    List<Services> getAllServicesBySubscriber(Long clientId, Integer subscriberID);

    Services createServiceBySubscriberID(Long clientId, Integer subscriberId, Services newService);
}

