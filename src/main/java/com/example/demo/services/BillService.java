package com.example.demo.services;

import com.example.demo.data.BillRepository;
import com.example.demo.data.SubscriberRepository;
import com.example.demo.entities.Bill;
import com.example.demo.entities.Services;
import com.example.demo.entities.Subscriber;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.base.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillService implements IBillService {

    BillRepository billRepository;
    SubscriberRepository subscriberRepository;

    @Autowired
    public BillService(BillRepository billRepository, SubscriberRepository subscriberRepository){
        this.billRepository = billRepository;
        this.subscriberRepository = subscriberRepository;
    }


    @Override
    public List<Bill> TenMostResentPaidBillsForASubscriber(String bankName) {
        return billRepository.TenMostResentPaidBillsForASubscriber(bankName);
    }

    @Override
    public Page<Bill> getAllUnpaidBills(Integer subscriberId, Pageable pageable) {
        return this.billRepository.findAllBySubscriber_IdAndPaymentDateIsNull(subscriberId,pageable);
    }

    @Override
    public Page<Bill> getAllPaidBills(Integer subscriberId,LocalDate paymentDate, Pageable pageable) {
        return this.billRepository.findAllBySubscriber_IdAndPaymentDateIsNotNull(subscriberId,paymentDate,pageable);
    }

    //TODO currency needed
    @Override
    public Bill createUnpaidBill(Integer subscriberID,Services givenService, Bill newBill) {
        Subscriber billSubscriber =  this.subscriberRepository.findById(subscriberID).
                orElseThrow(()->new ResourceNotFoundException("Subscriber","id",subscriberID));
       if(!billSubscriber.getServices().contains(givenService)){
           throw new ResourceNotFoundException("This subscriber has no such service service","service",givenService);
       }
       newBill.setSubscriber(billSubscriber);
       newBill.setService(givenService);
        newBill.setStartDate(LocalDate.now());
        newBill.setEndDate(LocalDate.now().plusMonths(1));
        newBill.setPaymentDate(null);

        return this.billRepository.save(newBill);

    }
}
