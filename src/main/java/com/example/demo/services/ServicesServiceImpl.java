package com.example.demo.services;

import com.example.demo.data.ServiceRepository;
import com.example.demo.entities.Services;
import com.example.demo.services.base.GenericService;
import com.example.demo.services.base.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicesServiceImpl implements ServicesService, GenericService<Services, String> {

    @Autowired
    ServiceRepository serviceRepository;

    public ServicesServiceImpl(ServiceRepository servicesRepository){
        this.serviceRepository = servicesRepository;
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
    public List<Services> findBySubscriberPhone(String phone) {
        return serviceRepository.findBySubscriberPhone(phone);
    }

    @Override
    public List<Services> findBySubscriberEGN(String egn) {
        return serviceRepository.findBySubscriberEGN(egn);
    }
}
