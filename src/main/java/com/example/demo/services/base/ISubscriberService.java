package com.example.demo.services.base;

import com.example.demo.entities.Subscriber;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISubscriberService {
    public List<Subscriber> getAll();
    public Subscriber getSubscriberByID(int id);

    Subscriber createSubscriber(Subscriber newSubscriber);
    ResponseEntity<?> deleteSubscriber(int id);

    Subscriber updateSubscriber(int id, Subscriber subscriber);
}
