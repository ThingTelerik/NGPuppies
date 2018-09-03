package com.example.demo.services;

import com.example.demo.data.ClientRepository;
import com.example.demo.data.ServiceRepository;
import com.example.demo.data.SubscriberRepository;
import com.example.demo.entities.Client;
import com.example.demo.entities.Services;
import com.example.demo.entities.Subscriber;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.ServicesDto;
import com.example.demo.services.base.GenericService;
import com.example.demo.services.base.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    //works
    @Override
    public Services createService(ServicesDto newService) {
        Services result = new Services(newService.getName());
        return serviceRepository.save(result);
    }

    @Override
    public Collection<Services> getAllServicesBySubscriber(Long clientId, Integer subscriberID) {
        Subscriber mySubscriber = this.subscriberService.getSubscriberByID(clientId,subscriberID);
       return mySubscriber.getServices();
    }

    @Override
    public Services createServiceBySubscriberID(Long clientId, Integer subscriberId, Services newService) {
        Services givenService = this.serviceRepository.findByName(newService.getName());
        Subscriber mySubscriber = this.subscriberRepository.findByBank_IdAndId(clientId,subscriberId);
        if(mySubscriber==null||givenService==null){
            throw new ResourceNotFoundException("No such service or subscriber","Service name or subscriber id",newService.getName()+subscriberId);
        }
        else {

            if(!mySubscriber.getServices().contains(givenService)){
                mySubscriber.getServices().add(givenService);
                return givenService;
            }

        }
        return givenService;
    }

    @Override
    public Services getByName(String name) {
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
    public Services getById(Long servicesId) {
        return serviceRepository.getById(servicesId);
    }

    @Override
    public List<ServicesDto> allPaidServicesBySubscriber(String phone) {
        List<ServicesDto> result =
        serviceRepository.allPaidServicesBySubscriber(phone).stream()
                .map(x-> {
                    ServicesDto servicesDto = new ServicesDto(x.getName());
                    return servicesDto;
                })
                .collect(Collectors.toList());
        return result;
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
