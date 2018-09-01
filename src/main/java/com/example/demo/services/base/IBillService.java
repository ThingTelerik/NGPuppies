package com.example.demo.services.base;

import com.example.demo.entities.Bill;
import com.example.demo.entities.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IBillService {

    List<Bill> TenMostResentPaidBillsForASubscriber(String bankName);

    Page<Bill> getAllUnpaidBills(Integer subscriberId, Pageable pageable);

    Page<Bill> getAllPaidBills(Integer subscriberId, Pageable pageable);

    Bill createUnpaidBill(Integer subscriberID, Services services, Bill newBill);

}
