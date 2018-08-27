package com.example.demo.services;

import com.example.demo.data.BillRepository;
import com.example.demo.entities.Bill;
import com.example.demo.services.base.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BillService implements IBillService {

    BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository){
        this.billRepository = billRepository;
    }


    @Override
    public Page<Bill> getAllUnpaidBills(Integer subscriberId, Pageable pageable) {
        return this.billRepository.findAllBySubscriber_IdAndPaymentDateIsNull(subscriberId,pageable);
    }

    @Override
    public Page<Bill> getAllPaidBills(Integer subscriberId, Pageable pageable) {
        return this.billRepository.findAllBySubscriber_IdAndPaymentDateIsNotNull(subscriberId,pageable);
    }
}
