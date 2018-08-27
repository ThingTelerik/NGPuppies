package com.example.demo.services.base;

import com.example.demo.entities.Subscriber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISubscriberService {

    Page<Subscriber> getAllSubscribersByClientsID(Long clientID, Pageable pageable);

    Subscriber createSubscriberByClientId(Long clientID, Subscriber subscriber);

    Subscriber updateSubscriberByClientID(Long clientID, Integer sID, Subscriber subscriberRequest);

    ResponseEntity<?> deleteSubscriberByClientID(Long clientID, Integer subscriberID);
}
