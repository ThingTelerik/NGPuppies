package com.example.demo.services.base;

import com.example.demo.entities.Services;

import java.util.List;

public interface ServicesService {

    List<Services> findBySubscriberPhone(String phone);

    List<Services> findBySubscriberEGN(String egn);
}

