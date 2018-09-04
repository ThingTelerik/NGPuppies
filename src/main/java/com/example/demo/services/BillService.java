package com.example.demo.services;

import com.example.demo.data.BillRepository;
import com.example.demo.data.ServiceRepository;
import com.example.demo.data.SubscriberRepository;
import com.example.demo.entities.Bill;
import com.example.demo.entities.Currency;
import com.example.demo.entities.Services;
import com.example.demo.entities.Subscriber;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.base.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillService implements IBillService {

    BillRepository billRepository;
    SubscriberRepository subscriberRepository;
    @Autowired
    ServiceRepository serviceRepository;


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
    public Page<Bill> getAllUnpaidBills(Long clientID,Integer subscriberId, Pageable pageable) {
        Subscriber mySubscriber = this.subscriberRepository.findByBank_IdAndId(clientID,subscriberId);
        if(mySubscriber==null){
            return null;
        }
        return this.billRepository.findAllBySubscriber_IdAndPaymentDateIsNull(mySubscriber.getId(),pageable);
    }

    @Override
    public Page<Bill> getAllPaidBills(Long clientID,Integer subscriberId, Pageable pageable) {
        Subscriber mySubscriber = this.subscriberRepository.findByBank_IdAndId(clientID,subscriberId);
        if(mySubscriber==null){
            return null;
        }
        return this.billRepository.findAllBySubscriber_IdAndPaymentDateIsNotNull(mySubscriber.getId(), pageable);
    }

    /**
     *
     * @param subscriberID
     * @param newBill
     * @return
     * Create unpaid bill - startDate, endDate, paymentDate are default
     *    
     */
    @Override
    public Bill createUnpaidBill(Integer subscriberID, Bill newBill) {
        Services givenService = newBill.getService();
        String checkedName = givenService.getName();
       Services checkService =  this.serviceRepository.findByName(checkedName);
                  Subscriber billSubscriber =  this.subscriberRepository.findById(subscriberID).
                orElseThrow(()->new ResourceNotFoundException("Subscriber","id",subscriberID));
       if(!billSubscriber.getServices().contains(checkService)){
           throw new ResourceNotFoundException("This subscriber has no such service","service",givenService);
       }
       newBill.setSubscriber(billSubscriber);
       newBill.setService(checkService);
        newBill.setStartDate(LocalDate.now());
        newBill.setEndDate(LocalDate.now().plusMonths(1));
        newBill.setPaymentDate(null);

        return this.billRepository.save(newBill);

    }

    @Override
    public Bill payBill(long id, Integer subscriberId, Long billId) {

        Subscriber mySubscriber = this.subscriberRepository.findByBank_IdAndId(id,subscriberId);
        if(mySubscriber==null){
            return null;
        }
       return this.billRepository.findBySubscriber_IdAndIdAndPaymentDateIsNull(subscriberId,billId)
               .map(bill->{
                   bill.setPaymentDate(LocalDate.now());
                   return billRepository.save(bill);
               })
                .orElseThrow(()->new ResourceNotFoundException("No such bill","Bill Id",billId));

    }

    @Override
    public List<Bill> payAllUnpaidBills(long id, Integer subscriberId) {
        List<Bill> afterPayment = new ArrayList<>();
        Subscriber mySubscriber = this.subscriberRepository.findByBank_IdAndId(id, subscriberId);
        if (mySubscriber == null) {
            throw new ResourceNotFoundException("Couldn't find subscriber with such id", "id", subscriberId);
        } else {
            List<Bill> unpaidBills = this.billRepository.findAllBySubscriber_IdAndPaymentDateIsNull(subscriberId);

            for (Bill bill: unpaidBills
                 ) {
                bill.setPaymentDate(LocalDate.now());

                this.billRepository.save(bill);
                afterPayment.add(bill);
            }


            }
            return  afterPayment;

        }
    }


