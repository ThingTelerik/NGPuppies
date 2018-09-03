package com.example.demo.services;

import com.example.demo.data.ClientRepository;
import com.example.demo.data.SubscriberRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Subscriber;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.base.ISubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberService implements ISubscriberService {

    @Autowired
    SubscriberRepository subscriberRepository;
    @Autowired
    ClientRepository clientRepository;

    public SubscriberService(SubscriberRepository subscriberRepository, ClientRepository clientRepository){
        this.subscriberRepository = subscriberRepository;
        this.clientRepository = clientRepository;
    }


    //Get all subscribers for a given client
    @Override
    public Page<Subscriber> getAllSubscribersByClientsID(Long clientID, Pageable pageable) {
        return subscriberRepository.findAllByBank_Id(clientID,pageable);

    }

    //create subscriber for a given client
    @Override
    public Subscriber createSubscriberByClientId(Long clientID, Subscriber subscriber) {
        return this.clientRepository.findById(clientID).map(client -> {
            subscriber.setBank(client);
            return subscriberRepository.save(subscriber);
        }).orElseThrow(() -> new ResourceNotFoundException("Client", "not found",clientID));
    }



    //update subscriber by for a given client
    @Override
    public Subscriber updateSubscriberByClientID(Long clientID, Integer sID, Subscriber subscriberRequest) {

        if(!clientRepository.existsById(clientID)) {
            throw new ResourceNotFoundException("Client ","Not found",clientID);
        }
        Subscriber mySubscriber = this.subscriberRepository.findByBank_IdAndId(clientID,sID);
        if(mySubscriber==null){
            throw new ResourceNotFoundException("Subscriber with such id doesn't exist for the current client","id",sID);
        }
        else {
            return subscriberRepository.findById(sID).map(subscriber -> {
                subscriber.setAddress(subscriberRequest.getAddress());
                subscriber.setEGN(subscriberRequest.getEGN());
                subscriber.setFirstName(subscriberRequest.getFirstName());
                subscriber.setLastName(subscriberRequest.getLastName());
                subscriber.setPhoneNumber(subscriberRequest.getPhoneNumber());
                return subscriberRepository.save(subscriber);
            }).orElseThrow(() -> new ResourceNotFoundException("Subscriber", "doesn't exist", sID));
        }
    }


    //delete subscriber by for a given client
    @Override
    public ResponseEntity<?> deleteSubscriberByClientID(Long clientID, Integer subscriberID) {
        if(!clientRepository.existsById(clientID)) {
            throw new ResourceNotFoundException("ClientID ","not Found:",clientID);
        }

        Subscriber mySubscriber = this.subscriberRepository.findByBank_IdAndId(clientID,subscriberID);
        if(mySubscriber==null){
            throw new ResourceNotFoundException("Subscriber with such id doesn't exist for the current client","id",subscriberID);
        }
        else {
            return subscriberRepository.findById(subscriberID).map(subscriber -> {
                subscriberRepository.delete(subscriber);
                return ResponseEntity.ok().build();
            }).orElseThrow(() -> new ResourceNotFoundException("Subscriber ", "not Found:", subscriberID));
        }
    }

    @Override
    public Subscriber getSubscriberByID(Long clientID, Integer subscriberId) {
        if(!clientRepository.existsById(clientID)){
            throw new ResourceNotFoundException("ClientID","Not Found",clientID);
        }

        return this.subscriberRepository.findByBank_IdAndId(clientID,subscriberId);


    }

}
