package com.example.demo.services;

import com.example.demo.data.ClientRepository;
import com.example.demo.data.ServiceRepository;
import com.example.demo.data.SubscriberRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Services;
import com.example.demo.entities.Subscriber;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.base.GenericService;
import com.example.demo.services.base.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicesServiceImpl implements ServicesService, GenericService<Services, String> {

   private ServiceRepository serviceRepository;
   private ClientRepository clientRepository;
   private SubscriberRepository subscriberRepository;
   private SubscriberService subscriberService;

    @Autowired
    public ServicesServiceImpl(SubscriberService subscriberService,ServiceRepository servicesRepository,ClientRepository clientRepository,    SubscriberRepository subscriberRepository){
        this.serviceRepository = servicesRepository;
        this.clientRepository = clientRepository;
        this.subscriberRepository = subscriberRepository;
        this.subscriberService = subscriberService;
    }

    @Override
    public List<Services> getAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Services createService(Services newService) {
        return this.serviceRepository.save(newService);
    }

    @Override
    public List<Services> getAllServicesBySubscriber(Long clientId, Integer subscriberID) {
        Subscriber mySubscriber = this.subscriberService.getSubscriberByID(clientId,subscriberID);
       return mySubscriber.getServices();
    }

    @Override
    public Services createServiceBySubscriberID(Long clientId, Integer subscriberId, Services newService) {
        //TODO implement method
        return null;
    }

    @Override
    public Services create(Services entity) {
        return serviceRepository.save(entity);
    }

    @Override
    public void delete(Services entity) {
        serviceRepository.delete(entity);
    }

    @Override
    public Services update(Services entity, String param) {
    return serviceRepository.updateServiceByName(entity, param);

    }

    @Override
    public List<Services> allPaidServicesBySubscriber(String phone) {
        return serviceRepository.allPaidServicesBySubscriber(phone);
    }

    @Override
    public List<Services> findBySubscriberPhone(String phone) {
        return serviceRepository.findBySubscriberPhone(phone);
    }

    @Override
    public List<Services> findBySubscriberEGN(String egn) {
        return serviceRepository.findBySubscriberEGN(egn);
    }
}
