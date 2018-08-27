package com.example.demo.services;

import com.example.demo.data.SubscriberRepository;
import com.example.demo.entities.Subscriber;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.base.ISubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberService implements ISubscriberService {

    SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository){
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public List<Subscriber> getAll() {
        return this.subscriberRepository.findAll();
    }

    @Override
    public Subscriber getSubscriberByID(int id) {

        return this.subscriberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscriber", "id", id));
    }

    @Override
    public Subscriber createSubscriber(Subscriber newSubscriber) {
        return subscriberRepository.save(newSubscriber);
    }

    @Override
    public ResponseEntity<?> deleteSubscriber(int id) {
        Subscriber s = subscriberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscriber", "id", id));

        subscriberRepository.delete(s);

        return ResponseEntity.ok().build();
    }

    @Override
    public Subscriber updateSubscriber(int id, Subscriber subscriber) {
        Subscriber newSubscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank", "id", id));
        newSubscriber.setEGN(subscriber.getEGN());
        newSubscriber.setFirstName(subscriber.getFirstName());
        newSubscriber.setLastName(subscriber.getLastName());
        newSubscriber.setPhoneNumber(subscriber.getPhoneNumber());
        newSubscriber.setAddress(subscriber.getAddress());
        Subscriber updatedSubscriber = subscriberRepository.save(newSubscriber);
        return updatedSubscriber;
    }
}